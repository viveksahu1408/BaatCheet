package com.example.baatcheet.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baatcheet.data.model.Conversation
import com.example.baatcheet.ui.search.SearchUserActivity
import com.example.baatcheet.databinding.ActivityChatListBinding
import com.example.baatcheet.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ChatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatListBinding
    private val viewModel: ChatListViewModel by viewModels()
    private lateinit var adapter: ChatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatListAdapter { conversation ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("conversationId", conversation.conversationId)
            intent.putExtra("otherUserId", getOtherUserId(conversation))
            startActivity(intent)
        }

        binding.btnSearchUser.setOnClickListener {
            startActivity(Intent(this, SearchUserActivity::class.java))
        }

        binding.fabSearch.setOnClickListener {
            startActivity(Intent(this, SearchUserActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.conversations.observe(this) { list ->
            adapter.submitList(list)
        }

        viewModel.fetchConversations()
    }

    private fun getOtherUserId(convo: Conversation): String {
        val currentUserId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        return if (convo.user1Id == currentUserId) convo.user2Id else convo.user1Id
    }
}