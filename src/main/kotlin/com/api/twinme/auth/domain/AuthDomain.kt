package com.api.twinme.auth.domain

import com.api.twinme.entity.Provider
import com.api.twinme.entity.UserEntity

data class User(
    val id: Long? = null,
    val provider: Provider,
    val encryptedSub: String,
    val hashedSub: String,
    val email: String,
    val nickname: String,
    val age: Int
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = null,
            provider = this.provider,
            encryptedSub = this.encryptedSub,
            hashedSub = this.hashedSub,
            email = this.email,
            nickname = this.nickname,
            age = this.age
        )
    }
}