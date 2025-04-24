package com.api.twinme.user.adapter.out.persistence

import com.api.twinme.user.domain.model.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun existsByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): Boolean

    fun findByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): UserEntity?

    fun findByHashedSub(
        hashedSub: String
    ): UserEntity?

}