package com.api.twinme.entity

import com.api.twinme.auth.domain.User
import com.api.twinme.exception.NotFoundProviderException
import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    @Column(name = "encrypted_sub")
    val encryptedSub: String,

    @Column(name = "hashed_sub")
    val hashedSub: String,

    val email: String,

    val nickname: String,

    val age: Int,

): AbstractBaseAuditEntity() {

    fun toModel(): User = User(
        id = this.id,
        provider = this.provider,
        encryptedSub = this.encryptedSub,
        hashedSub = this.hashedSub,
        email = this.email,
        nickname = this.nickname,
        age = this.age
    )

}

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