package com.api.twinme.auth.adapter.`in`.web.dto

import com.api.twinme.auth.domain.model.UserToken

data class UserTokenResponse(
    val accessToken: String,
    val refreshToken: String
)

fun UserTokenResponse.fromUserTokenModel(
    userToken: UserToken
): UserTokenResponse = UserTokenResponse(
    userToken.accessToken,
    userToken.refreshToken
)