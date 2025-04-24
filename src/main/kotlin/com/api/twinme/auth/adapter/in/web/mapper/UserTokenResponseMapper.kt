package com.api.twinme.auth.adapter.`in`.web.mapper

import com.api.twinme.auth.adapter.`in`.web.dto.UserTokenResponse
import com.api.twinme.auth.domain.model.UserToken

fun UserToken.toUserTokenResponse(): UserTokenResponse = UserTokenResponse(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)