package com.example.myapplication.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.entity.Post

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var postList: List<Post> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val postImageView = itemView.findViewById<ImageView>(R.id.postImageView)

        val postTitleView = itemView.findViewById<TextView>(R.id.postTitleView)

        val desctiptionView = itemView.findViewById<TextView>(R.id.descriptionTextView)

        val linkView = itemView.findViewById<TextView>(R.id.linkTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_post, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (postList[position].imageUrl != null) {
            holder.postImageView.isVisible = true
            Glide.with(holder.postImageView)
                .load(postList[position].imageUrl)
                .circleCrop()
                .into(holder.postImageView)
        } else {
            holder.postImageView.isVisible = false
        }

        if (postList[position].title != null) {
            holder.postTitleView.isVisible = true
            holder.postTitleView.text = postList[position].title
        } else {
            holder.postTitleView.isVisible = false
        }

        if (postList[position].text != null) {
            holder.desctiptionView.isVisible = true
            holder.desctiptionView.text = postList[position].text
        } else {
            holder.desctiptionView.isVisible = false
        }

        if (postList[position].linkUrl != null) {
            holder.linkView.isVisible = true
            holder.linkView.text = postList[position].linkUrl
        } else {
            holder.linkView.isVisible = false
        }

    }

    override fun getItemCount(): Int = postList.size
}