package com.api.twinme.rest

import com.api.twinme.rest.dto.ExistUserResponse
import com.api.twinme.rest.dto.SignInRequest
import com.api.twinme.rest.dto.SignUpRequest
import com.api.twinme.rest.dto.UserToken
import com.api.twinme.service.AuthService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(tags = ["Auth"])
@RestController
@RequestMapping("/api/v1/auth")
class AuthRest(
    private val authService: AuthService
) {

    // TODO : Refresh Token Redis 저장 필요

    @GetMapping("/exist")
    @ApiOperation("존재하는 회원인지 체크")
    fun existUser(
        @RequestParam sub: String,
        @RequestParam provider: String
    ): ResponseEntity<ExistUserResponse> {
        val isExist = authService.existUser(sub, provider)
        val response = ExistUserResponse(
            isExist = isExist
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    @PostMapping("/sign-up")
    @ApiOperation("회원가입")
    fun signUp(
        @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<UserToken> {
        val userToken = authService.signUp(signUpRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userToken)
    }

    @PostMapping("/sign-in")
    @ApiOperation("로그인")
    fun signIn(
        @RequestBody signInRequest: SignInRequest
    ): ResponseEntity<UserToken> {
        val userToken = authService.signIn(signInRequest)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userToken)
    }

}