package com.api.twinme.chat.adapter.`in`.web.rest

import com.api.twinme.chat.adapter.`in`.web.dto.ChatSendRequest
import com.api.twinme.chat.adapter.`in`.web.dto.FindChatListResponse
import com.api.twinme.chat.adapter.`in`.web.mapper.toFindChatListResponse
import com.api.twinme.chat.application.command.FindChatMessageCommand
import com.api.twinme.chat.application.command.SendChatCommand
import com.api.twinme.chat.application.usecase.ChatUseCase
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
    private val chatUseCase: ChatUseCase
) {

    @ApiOperation("채팅 API")
    @PostMapping("/send")
    fun<T> sendChat(
        @RequestBody request: ChatSendRequest,
        user: AuthenticatedUser
    ): ResponseEntity<T> {
        val command = SendChatCommand(request.message, userId = user.userId)
        chatUseCase.sendChat(command)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @ApiOperation("지난 채팅 조회 API (Cursor ID 기반)")
    @GetMapping
    fun findChatByCursor(
        @RequestParam(required = false) cursor: Long?,
        user: AuthenticatedUser
    ): ResponseEntity<FindChatListResponse> {
        val command = FindChatMessageCommand(cursor, user.userId)
        val result = chatUseCase.findChatByCursor(command)
        val response = result.toFindChatListResponse()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

}