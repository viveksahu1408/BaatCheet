package com.example.baatcheet.ui.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.data.model.Message
import com.example.baatcheet.databinding.ItemMessageReceiverBinding
import com.example.baatcheet.databinding.ItemMessageSenderBinding
import com.google.firebase.auth.FirebaseAuth


class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: List<Message> = emptyList()
    private val senderId = FirebaseAuth.getInstance().currentUser?.uid

    fun submitList(list: List<Message>) {
        messages = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderId == senderId) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemMessageSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SenderViewHolder(binding)
        } else {
            val binding = ItemMessageReceiverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiverViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        if (holder is SenderViewHolder) holder.bind(msg)
        else if (holder is ReceiverViewHolder) holder.bind(msg)
    }

    inner class SenderViewHolder(private val binding: ItemMessageSenderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Message) {
            binding.tvMessage.text = msg.message
        }
    }

    inner class ReceiverViewHolder(private val binding: ItemMessageReceiverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Message) {
            binding.tvMessage.text = msg.message
        }
    }
}