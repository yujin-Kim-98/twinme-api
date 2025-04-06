package com.api.twinme.entity

import com.api.twinme.exception.NotFoundProviderException
import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    @Column(name = "encrypted_sub")
    val encryptedSub: String,

    @Column(name = "hashed_sub")
    val hashedSub: String,

    val email: String,

    val nickname: String,

    val age: Int,

): AbstractBaseAuditEntity()

enum class Provider {
    GOOGLE;

    companion object {
        fun getProvider(
            value: String
        ): Provider = try {
            valueOf(value.uppercase())
        } catch (e: Exception) {
            throw NotFoundProviderException()
        }
    }
}