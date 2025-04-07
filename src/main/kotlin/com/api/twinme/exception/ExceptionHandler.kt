package com.api.twinme.exception

import com.api.twinme.auth.rest.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(ApiException::class)
    fun handleApiException(
        e: ApiException
    ) = ResponseEntity
        .status(e.errorCode.status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ErrorResponse(e.errorCode, e.errorCode.message)
        )

}