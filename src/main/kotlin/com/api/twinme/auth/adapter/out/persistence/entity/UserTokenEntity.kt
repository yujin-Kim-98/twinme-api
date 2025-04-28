package com.api.twinme.auth.adapter.out.persistence.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.annotation.Id

@RedisHash(value = "user-token")
data class UserTokenEntity(

    @Id
    val userId: Long,

    val refreshToken: String,

    @TimeToLive
    val expirationTime: Long? = null

)