package com.api.twinme.chat.rest.dto

import com.api.twinme.chat.domain.ChatMessage

data class GetChatResponse(
    val messages: List<ChatMessage>,
    val nextCursor: Long?
)