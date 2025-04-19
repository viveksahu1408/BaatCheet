package com.example.baatcheet

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerUser(email: String, password: String, username: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = auth.currentUser!!.uid
                val user = User(uid, username, email)
                db.collection("users").document(uid).set(user)
                    .addOnSuccessListener { callback(true, null) }
                    .addOnFailureListener { callback(false, it.message) }
            } else {
                callback(false, task.exception?.message)
            }
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) callback(true, null)
            else callback(false, task.exception?.message)
        }
    }
}