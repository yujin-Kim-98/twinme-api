package com.api.twinme.user.adapter.out.persistence

import com.api.twinme.user.domain.model.Provider
import com.api.twinme.user.domain.model.User
import com.api.twinme.user.domain.port.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository
): UserRepository {

    override fun existsByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): Boolean {
        return jpaRepository.existsByHashedSubAndProvider(hashedSub, provider)
    }

    override fun save(
        user: User
    ): User {
        return jpaRepository.save(
            UserEntity.fromModel(user)
        ).toModel()
    }

    override fun findByHashedSubAndProvider(
        hashedSub: String,
        provider: Provider
    ): User? {
        return jpaRepository.findByHashedSubAndProvider(
            hashedSub,
            provider
        )?.toModel()
    }

    override fun findByHashedSub(
        hashedSub: String
    ): User? {
        return jpaRepository.findByHashedSub(hashedSub)?.toModel()
    }

}