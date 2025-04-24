package com.api.twinme.user.domain.port

import com.api.twinme.user.domain.model.Provider
import com.api.twinme.user.domain.model.User

interface UserRepository {

    fun existsByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): Boolean

    fun save(
        user: User
    ): User

    fun findByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): User?

    fun findByHashedSub(
        hashedSub: String
    ): User?

}