package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.UserListBinding


class FavoriteAdapter (private val userList: List<FavoriteUser>, private val onItemClick:(user: FavoriteUser) -> Unit
): RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: FavoriteUser) = binding.apply {
            tvUsername.text = user.username
            Glide.with(this@ListViewHolder.itemView.context)
                .load(user.avatarUrl)
                .into(ivAvatar)

            root.setOnClickListener{
                onItemClick.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ListViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size


}