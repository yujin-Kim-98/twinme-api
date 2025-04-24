package com.api.twinme.auth.adapter.`in`.web.rest

import com.api.twinme.auth.adapter.`in`.web.dto.*
import com.api.twinme.auth.adapter.`in`.web.mapper.toUserTokenResponse
import com.api.twinme.auth.application.usecase.AuthUseCase
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthRest(
    private val authUseCase: AuthUseCase
) {

    // TODO : Refresh Token Redis 저장

    @GetMapping("/exist")
    @ApiOperation("존재하는 회원인지 체크")
    fun checkExist(
        params: CheckExistRequest
    ): ResponseEntity<CheckExistResponse> {
        val command = params.toCommand()
        val isExist = authUseCase.checkExistUser(command)
        val response = CheckExistResponse(isExist)

        return ResponseEntity
            .ok()
            .body(response)
    }

    @PostMapping("/sign-up")
    @ApiOperation("회원가입")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<UserTokenResponse> {
        val signUpCommand = request.toCommand()
        val userToken = authUseCase.signUp(signUpCommand)
        val response = userToken.toUserTokenResponse()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)
    }

    @PostMapping("/sign-in")
    @ApiOperation("로그인")
    fun signIn(
        @RequestBody request: SignInRequest
    ): ResponseEntity<UserTokenResponse> {
        val command = request.toCommand()
        val userToken = authUseCase.signIn(command)
        val response = userToken.toUserTokenResponse()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)
    }

}