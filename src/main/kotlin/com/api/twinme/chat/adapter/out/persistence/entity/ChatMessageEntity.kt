package com.api.twinme.chat.adapter.out.persistence.entity

import com.api.twinme.chat.domain.model.ChatMessage
import com.api.twinme.chat.domain.model.Sender
import com.api.twinme.common.entity.AbstractBaseAuditEntity
import javax.persistence.*

@Entity
@Table(name = "chat_message")
class ChatMessageEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "user_id")
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val sender: Sender,

    val content: String

): AbstractBaseAuditEntity() {
    fun toModel() = ChatMessage(
        id = this.id,
        userId = this.userId,
        sender = this.sender,
        content = this.content
    )
}