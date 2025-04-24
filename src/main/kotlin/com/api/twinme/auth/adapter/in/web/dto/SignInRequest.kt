package com.api.twinme.auth.adapter.`in`.web.dto

import com.api.twinme.auth.application.command.SignInCommand
import com.api.twinme.user.domain.model.Provider

data class SignInRequest(
    val sub: String,
    val provider: Provider
)

fun SignInRequest.toCommand(): SignInCommand = SignInCommand(
    this.sub,
    this.provider
)