package com.api.twinme.auth.repository

import com.api.twinme.auth.domain.User
import com.api.twinme.entity.Provider
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository {

    override fun findByHashedSub(
        hashedSub: String
    ): User? {
        return userJpaRepository.findByHashedSub(hashedSub)?.toModel()
    }

    override fun findByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): User? {
        return userJpaRepository.findByHashedSubAndProvider(hashedSub, provider)?.toModel()
    }

    override fun existsByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): Boolean {
        return userJpaRepository.existsByHashedSubAndProvider(hashedSub, provider)
    }

    override fun save(
        user: User
    ): User {
        return userJpaRepository.save(user.toEntity()).toModel()
    }

}