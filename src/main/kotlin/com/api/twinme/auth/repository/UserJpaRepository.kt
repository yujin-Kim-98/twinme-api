package com.api.twinme.auth.repository

import com.api.twinme.entity.Provider
import com.api.twinme.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<UserEntity, Long> {

    fun findByHashedSub(hashedSub: String): UserEntity?

    fun findByHashedSubAndProvider(hashedSub: String, provider: Provider): UserEntity?

    fun existsByHashedSubAndProvider(hashedSub: String, provider: Provider): Boolean

}