package com.example.woi.test.presentation.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woi.test.R
import com.example.woi.test.utils.Post
import kotlinx.android.synthetic.main.activity_post_comment.*

class PostActivity : AppCompatActivity(), PostView {
    override fun showPost(posts: List<Post>) {
        (rvPost.adapter as PostAdapter).posts = posts
        rvPost.adapter?.notifyDataSetChanged()
    }

    override fun error(errorMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comment)

        rvPost.adapter = PostAdapter(emptyList())
        rvPost.layoutManager = LinearLayoutManager(this)
    }
}
