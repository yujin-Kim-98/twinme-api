package com.api.twinme.auth.infra.redis

import com.api.twinme.auth.adapter.out.persistence.entity.UserTokenEntity
import com.api.twinme.auth.adapter.out.persistence.repository.UserTokenRedisRepository
import com.api.twinme.auth.domain.model.UserToken
import com.api.twinme.auth.domain.port.out.AuthCachePort
import com.api.twinme.common.exception.FailedTokenStoredException
import com.api.twinme.user.domain.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AuthCacheAdapter(
    private val userTokenRedisRepository: UserTokenRedisRepository,
    @Value("\${jwt.refresh-token-expiration}") private val expirationTime: Long
): AuthCachePort {

    override fun saveRefreshToken(
        user: User,
        userToken: UserToken
    ) {
        user.id?.let { userId ->
            val userTokenEntity = UserTokenEntity(
                userId = userId,
                refreshToken = userToken.refreshToken,
                expirationTime = expirationTime
            )
            userTokenRedisRepository.save(userTokenEntity)
        } ?: throw FailedTokenStoredException()
    }

}