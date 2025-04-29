package com.api.twinme.chat.adapter.`in`.web.dto

import com.api.twinme.chat.domain.model.ChatMessage

data class FindChatListResponse(
    val messages: List<ChatMessage>,
    val nextCursorId: Long?
)