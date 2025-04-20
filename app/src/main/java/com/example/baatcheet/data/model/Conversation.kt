package com.example.baatcheet.data.model

data class Conversation(
    val conversationId: String = "",
    val user1Id: String = "",
    val user2Id: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L
)