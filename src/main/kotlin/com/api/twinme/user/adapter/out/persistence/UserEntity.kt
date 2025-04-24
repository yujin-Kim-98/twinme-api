package com.api.twinme.user.adapter.out.persistence

import com.api.twinme.common.entity.AbstractBaseAuditEntity
import com.api.twinme.user.domain.model.Provider
import com.api.twinme.user.domain.model.User
import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = -1,

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

    companion object {
        fun fromModel(
            user: User
        ): UserEntity {
            return UserEntity(
                provider = user.provider,
                encryptedSub = user.encryptedSub,
                hashedSub = user.hashedSub,
                email = user.email,
                nickname = user.nickname,
                age = user.age
            )
        }
    }

    fun toModel() = User(
        id = this.id,
        provider = this.provider,
        encryptedSub = this.encryptedSub,
        hashedSub = this.hashedSub,
        email = this.email,
        nickname = this.nickname,
        age = this.age
    )

}