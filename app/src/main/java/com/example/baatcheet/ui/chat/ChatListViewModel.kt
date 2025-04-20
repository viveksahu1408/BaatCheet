package com.example.baatcheet.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baatcheet.data.model.Conversation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatListViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _conversations = MutableLiveData<List<Conversation>>()
    val conversations: LiveData<List<Conversation>> get() = _conversations

    fun fetchConversations() {
        val currentUserId = auth.currentUser?.uid ?: return
        db.collection("conversations")
            .whereArrayContains("users", currentUserId) // You can store `users: [user1, user2]`
            .addSnapshotListener { snapshots, error ->
                if (error != null) return@addSnapshotListener
                val result = snapshots?.documents?.mapNotNull { doc ->
                    doc.toObject(Conversation::class.java)?.copy(conversationId = doc.id)
                } ?: emptyList()
                _conversations.postValue(result)
            }
    }
}