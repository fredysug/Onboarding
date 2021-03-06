package com.example.woi.test.presentation.users

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woi.test.R
import com.example.woi.test.data.UserRepositoryImpl
import com.example.woi.test.data.remote.RemoteGatewayImpl
import com.example.woi.test.domain.GetAllUserUseCaseImpl
import com.example.woi.test.presentation.post.PostActivity
import com.example.woi.test.utils.User
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity(), UserView, OnItemClickListener {

    private val gateway = RemoteGatewayImpl()
    private val repository = UserRepositoryImpl(gateway)
    private val useCase = GetAllUserUseCaseImpl(repository)
    private var presenter = UserPresenterImpl(useCase)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        rvUsers.adapter = UserAdapter(emptyList(), this)
        rvUsers.layoutManager = LinearLayoutManager(this)

        presenter.attach(this)
        presenter.loadUsers()
    }

    override fun showAllUser(users: List<User>) {
        (rvUsers.adapter as UserAdapter).apply {
            this.users = users
            this.notifyDataSetChanged()
        }
    }

    override fun onItemClick(user: User) {
        openPost(user.id.toString())
    }

    override fun showErrorMessage(errorMessage: String) {
        AlertDialog.Builder(this).setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun openPost(userId: String) {
        intent = Intent(this, PostActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }

}

