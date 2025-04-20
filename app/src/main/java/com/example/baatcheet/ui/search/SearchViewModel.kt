package com.example.baatcheet.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baatcheet.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val _searchResults = MutableLiveData<List<User>>()
    val searchResults: LiveData<List<User>> get() = _searchResults

    fun searchUsers(query: String) {
        db.collection("users")
            .get()
            .addOnSuccessListener { snapshot ->
                val results = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
                    .filter { it.uid != currentUserId && (it.username.contains(query, true) || it.email.contains(query, true)) }
                _searchResults.postValue(results)
            }
    }

    fun startConversationWith(user: User, callback: (String) -> Unit) {
        val convoId = if (currentUserId < user.uid)
            "${currentUserId}_${user.uid}"
        else
            "${user.uid}_${currentUserId}"

        val convoRef = db.collection("conversations").document(convoId)

        convoRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                val convo = hashMapOf(
                    "user1Id" to currentUserId,
                    "user2Id" to user.uid,
                    "lastMessage" to "",
                    "timestamp" to System.currentTimeMillis(),
                    "users" to listOf(currentUserId, user.uid)
                )
                convoRef.set(convo).addOnSuccessListener {
                    callback(convoId)
                }
            } else {
                callback(convoId)
            }
        }
    }
}