package com.api.twinme.auth.repository

import com.api.twinme.auth.domain.User
import com.api.twinme.entity.Provider

interface UserRepository {

    fun findByHashedSub(hashedSub: String): User?

    fun findByHashedSubAndProvider(hashedSub: String, provider: Provider): User?

    fun existsByHashedSubAndProvider(hashedSub: String, provider: Provider): Boolean

    fun save(user: User): User

}