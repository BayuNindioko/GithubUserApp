package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.UserListBinding
import com.example.githubuser.models.ItemsItem

class UserAdapter (private val userList: List<ItemsItem>, private val onItemClick:(user:ItemsItem) -> Unit
): RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: ItemsItem) = binding.apply {
                tvUsername.text = user.login
                Glide.with(this@ListViewHolder.itemView.context)
                    .load(user.avatarUrl)
                    .into(ivAvatar)

            root.setOnClickListener{
                onItemClick.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ListViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size

}