package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var userList: List<User> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val avatarImageView = itemView.findViewById<ImageView>(R.id.avatarImageView)

        val userNameTextView = itemView.findViewById<TextView>(R.id.userNameTextView)

        val groupNameTextView = itemView.findViewById<TextView>(R.id.groupNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_user, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.avatarImageView)
            .load(userList[position].avatarUrl)
            .circleCrop()
            .into(holder.avatarImageView)

        holder.userNameTextView.text = userList[position].userName
        holder.groupNameTextView.text = userList[position].groupName
    }

    override fun getItemCount(): Int = userList.size
}