package com.api.twinme.auth.adapter.`in`.web.dto

import com.api.twinme.auth.application.command.CheckExistUserCommand
import com.api.twinme.user.domain.model.Provider

data class CheckExistRequest(
    val sub: String,
    val provider: Provider
)
fun CheckExistRequest.toCommand(): CheckExistUserCommand = CheckExistUserCommand(
    this.sub,
    this.provider
)