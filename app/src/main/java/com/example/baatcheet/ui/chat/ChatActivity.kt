package com.example.baatcheet.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baatcheet.ui.register.MessageAdapter
import com.example.baatcheet.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: MessageAdapter
    private lateinit var conversationId: String
    private lateinit var otherUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        conversationId = intent.getStringExtra("conversationId") ?: ""
        otherUserId = intent.getStringExtra("otherUserId") ?: ""

        adapter = MessageAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.loadMessages(conversationId)

        viewModel.messages.observe(this) { msgs ->
            adapter.submitList(msgs)
            binding.recyclerView.scrollToPosition(msgs.size - 1)
        }

        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.sendMessage(conversationId, otherUserId, text)
                binding.etMessage.text?.clear()
            }
        }
    }
}