package com.example.baatcheet.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.data.model.User
import com.example.baatcheet.databinding.ItemUserBinding

class SearchUserAdapter(
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<SearchUserAdapter.UserViewHolder>() {

    private var users: List<User> = emptyList()

    fun submitList(list: List<User>) {
        users = list
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = user.username
            binding.tvEmail.text = user.email

            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }
}
