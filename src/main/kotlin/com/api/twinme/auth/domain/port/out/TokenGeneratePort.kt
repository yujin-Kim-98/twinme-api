package com.api.twinme.auth.domain.port.out

import com.api.twinme.auth.domain.model.UserToken
import com.api.twinme.user.domain.model.User

interface TokenGeneratePort {

    fun generateToken(
        user: User
    ): UserToken

    fun hashWithSHA256(
        input: String
    ): String

}