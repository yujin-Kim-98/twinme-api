package com.api.twinme.auth.application.command

import com.api.twinme.user.domain.model.Provider

data class CheckExistUserCommand(
    val sub: String,
    val provider: Provider
)