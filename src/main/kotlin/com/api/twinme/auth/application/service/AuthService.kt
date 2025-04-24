package com.api.twinme.auth.application.service

import com.api.twinme.auth.application.command.*
import com.api.twinme.auth.application.usecase.AuthUseCase
import com.api.twinme.auth.domain.model.UserToken
import com.api.twinme.auth.domain.port.out.TokenGeneratePort
import com.api.twinme.auth.infra.jwt.JwtTokenUtils
import com.api.twinme.common.config.security.jwt.JwtUser
import com.api.twinme.common.exception.ExistUserException
import com.api.twinme.common.exception.NotFoundUserException
import com.api.twinme.user.domain.model.User
import com.api.twinme.user.domain.port.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val tokenGeneratePort: TokenGeneratePort,
    private val userRepository: UserRepository,
): AuthUseCase {

    override fun checkExistUser(
        command: CheckExistUserCommand
    ): Boolean {
        val hashedSub = tokenGeneratePort.hashWithSHA256(command.sub)
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
        val hashedSub = tokenGeneratePort.hashWithSHA256(command.sub)

        val checkExistUserCommand = CheckExistUserCommand(command.sub, command.provider)
        when (checkExistUser(checkExistUserCommand)) {
            true -> throw ExistUserException()
            false -> {
                var user = command.toModel(
                    encryptedSub = passwordEncoder.encode(command.sub),
                    hashedSub = hashedSub
                )
                user = userRepository.save(user)
                return tokenGeneratePort.generateToken(user)
            }
        }
    }

    @Transactional
    override fun signIn(
        command: SignInCommand
    ): UserToken {
        val hashedSub = tokenGeneratePort.hashWithSHA256(command.sub)
        userRepository.findByHashedSubAndProvider(
            hashedSub,
            command.provider
        )?.let { user ->
            return tokenGeneratePort.generateToken(user)
        } ?: throw NotFoundUserException()
    }

}