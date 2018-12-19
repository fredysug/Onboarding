package com.example.woi.test.presentation.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woi.test.R
import com.example.woi.test.utils.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(var users: List<User>, val listener: OnItemClickListener) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_user, p0, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.itemView.username.text = users[position].username
        userViewHolder.itemView.setOnClickListener {
            listener.onItemClick(users[position])
        }
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

interface OnItemClickListener {
    fun onItemClick(user: User)
}
