package com.api.twinme.common.dto

import com.api.twinme.common.exception.ErrorCode

data class ErrorResponse(
    val errorCode: ErrorCode,
    val errorMessage: String
)