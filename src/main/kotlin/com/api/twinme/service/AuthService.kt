package com.api.twinme.service

import com.api.twinme.config.security.jwt.JwtTokenUtils
import com.api.twinme.entity.Provider
import com.api.twinme.entity.UserEntity
import com.api.twinme.exception.ExistUserException
import com.api.twinme.exception.NotFoundUserException
import com.api.twinme.repository.UserRepository
import com.api.twinme.rest.dto.SignInRequest
import com.api.twinme.rest.dto.SignUpRequest
import com.api.twinme.rest.dto.UserToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val userDetailsService: UserDetailsService,
    private val jwtTokenUtils: JwtTokenUtils
) {

    // TODO : Refresh Token Redis 적용 필요

    fun existUser(
        sub: String,
        provider: String
    ): Boolean {
        val hashedSub = hashWithSHA256(sub)
        return userRepository.existsByHashedSubAndProvider(
            hashedSub = hashedSub,
            provider = Provider.getProvider(provider)
        )
    }

    fun signUp(
        request: SignUpRequest
    ): UserToken {

        val hashedSub = hashWithSHA256(request.sub)
        val isExist = userRepository.existsByHashedSubAndProvider(
            hashedSub = hashedSub,
            provider = Provider.getProvider(request.provider)
        )

        when (isExist) {
            true -> throw ExistUserException()
            false -> {
                val userEntity = UserEntity(
                    provider = Provider.getProvider(request.provider),
                    encryptedSub = passwordEncoder.encode(request.sub),
                    hashedSub = hashedSub,
                    email = request.email,
                    nickname = request.nickname,
                    age = request.age
                )
                userRepository.save(userEntity)

                val userDetails = userDetailsService.loadUserByUsername(userEntity.hashedSub)

                return UserToken(
                    accessToken = jwtTokenUtils.generateToken(userDetails),
                    refreshToken = jwtTokenUtils.generateRefreshToken(userDetails)
                )
            }
        }

    }

    fun signIn(
        request: SignInRequest
    ): UserToken {

        userRepository.findByHashedSubAndProvider(
            hashedSub = hashWithSHA256(request.sub),
            provider = Provider.getProvider(request.provider)
        )?.let { userEntity ->
            val userDetails = userDetailsService.loadUserByUsername(userEntity.hashedSub)
            return UserToken(
                accessToken = jwtTokenUtils.generateToken(userDetails),
                refreshToken = jwtTokenUtils.generateRefreshToken(userDetails)
            )
        } ?: throw NotFoundUserException()

    }

    private fun hashWithSHA256(
        input: String
    ): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

}