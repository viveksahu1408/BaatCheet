package com.example.baatcheet.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.data.model.Conversation
import com.example.baatcheet.databinding.ItemConversationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatListAdapter(
    private val onItemClick: (Conversation) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    private var chatList: List<Conversation> = emptyList()

    fun submitList(list: List<Conversation>) {
        chatList = list
        notifyDataSetChanged()
    }

    inner class ChatViewHolder(val binding: ItemConversationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Conversation) {
            binding.tvLastMessage.text = item.lastMessage
            binding.tvTime.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(item.timestamp))


            binding.tvUsername.text = getOtherUserId(item)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }

        private fun getOtherUserId(item: Conversation): String {
            val currentUserId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
            return if (item.user1Id == currentUserId) item.user2Id else item.user1Id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }
}