package com.example.woi.test.presentation.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woi.test.R
import com.example.woi.test.utils.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(var users: List<User>) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onBindViewHolder(p0: UserViewHolder, p1: Int) {
        p0.itemView.username.text = users[p1].username
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_user, p0, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)