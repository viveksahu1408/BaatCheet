package com.example.baatcheet.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baatcheet.databinding.ActivitySearchUserBinding
import com.example.baatcheet.ui.chat.ChatActivity

class SearchUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchUserBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchUserAdapter { user ->
            viewModel.startConversationWith(user) { convoId ->
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("conversationId", convoId)
                intent.putExtra("otherUserId", user.uid)
                startActivity(intent)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            viewModel.searchUsers(query)
        }

        viewModel.searchResults.observe(this) {
            adapter.submitList(it)
        }

    }
}