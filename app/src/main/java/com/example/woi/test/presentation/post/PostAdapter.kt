package com.example.woi.test.presentation.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woi.test.R
import com.example.woi.test.utils.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(var posts: List<Post>) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.postTitle.text = posts[position].title
        holder.itemView.postBody.text = posts[position].body
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)