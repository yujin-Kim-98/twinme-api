package com.api.twinme.auth.application.usecase

import com.api.twinme.auth.application.command.CheckExistUserCommand
import com.api.twinme.auth.application.command.SignInCommand
import com.api.twinme.auth.application.command.SignUpCommand
import com.api.twinme.auth.domain.model.UserToken

interface AuthUseCase {

    fun checkExistUser(
        command: CheckExistUserCommand
    ): Boolean

    fun signUp(
        command: SignUpCommand
    ): UserToken

    fun signIn(
        command: SignInCommand
    ): UserToken

}