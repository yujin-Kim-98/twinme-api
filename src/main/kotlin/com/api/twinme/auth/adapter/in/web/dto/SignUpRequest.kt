package com.api.twinme.auth.adapter.`in`.web.dto

import com.api.twinme.auth.application.command.SignUpCommand
import com.api.twinme.user.domain.model.Provider
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class SignUpRequest(
    val sub: String,
    val provider: Provider,
    val email: String,
    val nickname: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate
)

fun SignUpRequest.toCommand() = SignUpCommand(
    this.sub,
    this.provider,
    this.email,
    this.nickname,
    this.birthDate
)