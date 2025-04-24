package com.api.twinme.auth.application.command

import com.api.twinme.user.domain.model.Provider
import com.api.twinme.user.domain.model.User
import java.time.LocalDate

data class SignUpCommand(
    val sub: String,
    val provider: Provider,
    val email: String,
    val nickname: String,
    val birthDate: LocalDate
)

fun SignUpCommand.toModel(
    encryptedSub: String,
    hashedSub: String
): User = User(
    provider = this.provider,
    encryptedSub = encryptedSub,
    hashedSub = hashedSub,
    email = this.email,
    nickname = this.nickname,
    birthDate = this.birthDate
)
