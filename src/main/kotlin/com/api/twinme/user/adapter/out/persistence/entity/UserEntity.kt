package com.api.twinme.user.adapter.out.persistence.entity

import com.api.twinme.common.entity.AbstractBaseAuditEntity
import com.api.twinme.user.domain.model.Provider
import com.api.twinme.user.domain.model.User
import java.time.LocalDate
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

    @Column(name = "birth_date")
    val birthDate: LocalDate

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
                birthDate = user.birthDate
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
        birthDate = this.birthDate
    )

}