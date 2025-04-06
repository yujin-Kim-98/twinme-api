package com.api.twinme.rest.dto

import com.api.twinme.exception.ErrorCode

data class ErrorResponse(
    val errorCode: ErrorCode,
    val errorMessage: String
)

data class UserToken(
    val accessToken: String,
    val refreshToken: String
)

data class ExistUserResponse(
    val isExist: Boolean
)