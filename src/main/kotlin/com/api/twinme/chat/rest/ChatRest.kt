package com.api.twinme.chat.rest

import com.api.twinme.chat.rest.dto.ChatRequest
import com.api.twinme.chat.rest.dto.GetChatResponse
import com.api.twinme.chat.service.ChatService
import com.api.twinme.common.config.web.AuthenticatedUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(tags = ["Chat"])
@RestController
@RequestMapping("/api/v1/chat")
class ChatRest(
    private val chatService: ChatService
) {

    @ApiOperation("채팅 API")
    @PostMapping
    fun chat(
        @RequestBody request: ChatRequest,
        user: AuthenticatedUser
    ) {
        chatService.chat(request, user.userId)
    }

    @ApiOperation("지난 채팅 조회 API (Cursor ID 기반)")
    @GetMapping
    fun getChat(
        @RequestParam(required = false) cursor: Long?,
        user: AuthenticatedUser
    ): ResponseEntity<GetChatResponse> {
        val response = chatService.getChat(cursor, user.userId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

}