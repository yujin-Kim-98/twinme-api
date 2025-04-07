package com.api.twinme.auth.service

import com.api.twinme.auth.domain.User
import com.api.twinme.auth.utils.JwtTokenUtils
import com.api.twinme.entity.Provider
import com.api.twinme.exception.ExistUserException
import com.api.twinme.exception.NotFoundUserException
import com.api.twinme.auth.repository.UserRepository
import com.api.twinme.auth.rest.dto.*
import com.api.twinme.config.security.jwt.JwtUser
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userPersistService: UserPersistService,
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

    @Transactional
    fun signUp(
        request: SignUpRequest
    ): SignInResponse {

        val hashedSub = hashWithSHA256(request.sub)

        when (existUser(request.sub, request.provider)) {
            true -> throw ExistUserException()
            false -> {
                val user = userPersistService.createUser(
                    User(
                        provider = Provider.getProvider(request.provider),
                        encryptedSub = passwordEncoder.encode(request.sub),
                        hashedSub = hashedSub,
                        email = request.email,
                        nickname = request.nickname,
                        age = request.age
                    )
                )

                val userToken = generateUserToken(user.hashedSub)
                val userInfo = UserInfo(
                    id = user.id!!,
                    email = user.email,
                    nickname = user.nickname,
                    age = user.age
                )

                return SignInResponse(userToken, userInfo)
            }
        }

    }

    @Transactional
    fun signIn(
        request: SignInRequest
    ): SignInResponse {

        userRepository.findByHashedSubAndProvider(
            hashedSub = hashWithSHA256(request.sub),
            provider = Provider.getProvider(request.provider)
        )?.let { user ->
            val userToken = generateUserToken(user.hashedSub)
            val userInfo = UserInfo(
                id = user.id!!,
                email = user.email,
                nickname = user.nickname,
                age = user.age
            )
            return SignInResponse(userToken, userInfo)
        } ?: throw NotFoundUserException()

    }

    fun hashWithSHA256(
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