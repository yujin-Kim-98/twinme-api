package com.api.twinme.common.config.security.jwt

import com.api.twinme.user.domain.model.User
import org.springframework.security.core.GrantedAuthority

class JwtUserFactory {

    companion object {
        fun create(
            user: User
        ): JwtUser {
            return JwtUser(
                id = user.id!!,
                sub = user.hashedSub,
                nickname = user.nickname,
                email = user.email,
                age = user.age,
                authorities = mapToGrantedAuthorities()
            )
        }

        private fun mapToGrantedAuthorities(): MutableList<GrantedAuthority> = mutableListOf()
    }

}