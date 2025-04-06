package com.api.twinme.config.security.jwt

import com.api.twinme.entity.UserEntity
import org.springframework.security.core.GrantedAuthority

class JwtUserFactory {

    companion object {
        fun create(
            user: UserEntity
        ): JwtUser {
            return JwtUser(
                id = user.id,
                sub = user.encryptedSub,
                nickname = user.nickname,
                email = user.email,
                age = user.age,
                authorities = mapToGrantedAuthorities()
            )
        }

        private fun mapToGrantedAuthorities(): MutableList<GrantedAuthority> = mutableListOf()
    }

}