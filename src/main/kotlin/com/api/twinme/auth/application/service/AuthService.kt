package com.api.twinme.auth.application.service

import com.api.twinme.auth.application.command.CheckExistUserCommand
import com.api.twinme.auth.application.command.SignInCommand
import com.api.twinme.auth.application.command.SignUpCommand
import com.api.twinme.auth.application.command.toModel
import com.api.twinme.auth.application.usecase.AuthUseCase
import com.api.twinme.auth.domain.model.UserToken
import com.api.twinme.auth.infra.jwt.JwtTokenUtils
import com.api.twinme.common.config.security.jwt.JwtUser
import com.api.twinme.common.exception.ExistUserException
import com.api.twinme.common.exception.NotFoundUserException
import com.api.twinme.user.domain.port.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService,
    private val jwtTokenUtils: JwtTokenUtils,
    private val userRepository: UserRepository
): AuthUseCase {

    override fun checkExistUser(
        command: CheckExistUserCommand
    ): Boolean {
        val hashedSub = hashWithSHA256(command.sub)
        val isExist = userRepository.existsByHashedSubAndProvider(
            hashedSub = hashedSub,
            provider = command.provider
        )
        return isExist
    }

    @Transactional
    override fun signUp(
        command: SignUpCommand
    ): UserToken {
        val hashedSub = hashWithSHA256(command.sub)

        val checkExistUserCommand = CheckExistUserCommand(command.sub, command.provider)
        when (checkExistUser(checkExistUserCommand)) {
            true -> throw ExistUserException()
            false -> {
                val user = command.toModel(
                    encryptedSub = passwordEncoder.encode(command.sub),
                    hashedSub = hashedSub
                )
                userRepository.save(user)
                return generateUserToken(hashedSub)
            }
        }
    }

    @Transactional
    override fun signIn(
        command: SignInCommand
    ): UserToken {
        val hashedSub = hashWithSHA256(command.sub)
        userRepository.findByHashedSubAndProvider(
            hashedSub,
            command.provider
        )?.let { user ->
            return generateUserToken(user.hashedSub)
        } ?: throw NotFoundUserException()
    }

    // TODO : Utils?
    private fun hashWithSHA256(
        input: String
    ): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun generateUserToken(
        hashedSub: String,
    ): UserToken {
        val jwtUser = userDetailsService.loadUserByUsername(hashedSub) as JwtUser

        return UserToken(
            accessToken = jwtTokenUtils.generateToken(hashedSub, jwtUser),
            refreshToken = jwtTokenUtils.generateRefreshToken(hashedSub, jwtUser)
        )
    }

}