package com.api.twinme.rest.dto

data class SignUpRequest(
    val sub: String,
    val provider: String,
    val email: String,
    val nickname: String,
    val age: Int
)

data class SignInRequest(
    val sub: String,
    val provider: String
)