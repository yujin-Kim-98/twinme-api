package com.api.twinme.config.security.jwt

import com.api.twinme.exception.ErrorCode
import com.api.twinme.auth.rest.dto.ErrorResponse
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {

    companion object {
        val unauthorizedResponse = ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                ErrorResponse(
                    errorCode = ErrorCode.UNAUTHORIZED,
                    errorMessage = ErrorCode.UNAUTHORIZED.message
                )
            )
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(Gson().toJson(unauthorizedResponse.body))
    }

}