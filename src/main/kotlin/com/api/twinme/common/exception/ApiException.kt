package com.api.twinme.common.exception

import org.springframework.http.HttpStatus

open class ApiException(
    val errorCode: ErrorCode,
    override val message: String? = null
): RuntimeException(
    message ?: errorCode.message
)

class NotFoundUserException: ApiException(ErrorCode.NOT_FOUND_USER)
class ExistUserException: ApiException(ErrorCode.EXIST_USER)
class NotFoundProviderException: ApiException(ErrorCode.NOT_FOUND_PROVIDER)

class FailedTokenStoredException: ApiException(ErrorCode.FAILED_TOKEN_STORED)

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "unauthorized"),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),
    NOT_FOUND_PROVIDER(HttpStatus.NOT_FOUND, "존재하지 않는 Provider입니다."),
    FAILED_TOKEN_STORED(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 Redis 저장이 실패했습니다.")
}