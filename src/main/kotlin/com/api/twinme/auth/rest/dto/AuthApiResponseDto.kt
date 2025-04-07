package com.api.twinme.auth.rest.dto

import com.api.twinme.exception.ErrorCode

data class ErrorResponse(
    val errorCode: ErrorCode,
    val errorMessage: String
)

data class SignInResponse(
    val userToken: UserToken,
    val userInfo: UserInfo
)

data class UserToken(
    val accessToken: String,
    val refreshToken: String
)

data class UserInfo(
    val id: Long,
    val email: String,
    val nickname: String,
    val age: Int
)

data class ExistUserResponse(
    val isExist: Boolean
)