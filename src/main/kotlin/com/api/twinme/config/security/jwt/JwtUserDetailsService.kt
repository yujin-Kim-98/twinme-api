package com.api.twinme.config.security.jwt

import com.api.twinme.exception.NotFoundUserException
import com.api.twinme.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service(value = "userDetailsService")
class JwtUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(
        username: String
    ): UserDetails {
        val user = userRepository.findByHashedSub(username)
            ?: throw NotFoundUserException()

        return JwtUserFactory.create(user)
    }

}