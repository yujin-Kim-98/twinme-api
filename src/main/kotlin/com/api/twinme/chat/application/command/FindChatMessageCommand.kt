package com.api.twinme.chat.application.command

data class FindChatMessageCommand(
    val cursor: Long? = 20L,
    val userId: Long
)