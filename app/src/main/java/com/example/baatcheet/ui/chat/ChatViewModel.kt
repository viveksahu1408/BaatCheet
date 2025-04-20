package com.example.baatcheet.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baatcheet.data.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class ChatViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    fun sendMessage(convoId: String, receiverId: String, messageText: String) {
        val messageId = UUID.randomUUID().toString()
        val message = Message(
            messageId = messageId,
            senderId = currentUserId ?: "",
            receiverId = receiverId,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )

        db.collection("conversations")
            .document(convoId)
            .collection("messages")
            .document(messageId)
            .set(message)

        // update conversation preview
        db.collection("conversations")
            .document(convoId)
            .update(mapOf(
                "lastMessage" to messageText,
                "timestamp" to message.timestamp
            ))
    }

    fun loadMessages(convoId: String) {
        db.collection("conversations")
            .document(convoId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val msgs = snapshot?.documents?.mapNotNull {
                    it.toObject(Message::class.java)
                } ?: emptyList()
                _messages.postValue(msgs)
            }
    }
}