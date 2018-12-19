package com.example.woi.test.presentation.users

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woi.test.R
import com.example.woi.test.data.UserRepositoryImpl
import com.example.woi.test.data.remote.RemoteGatewayImpl
import com.example.woi.test.domain.GetAllUserUseCaseImpl
import com.example.woi.test.presentation.MainPresenterImpl
import com.example.woi.test.presentation.View
import com.example.woi.test.utils.User
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity(), View {

    private val gateway = RemoteGatewayImpl()
    private val repository = UserRepositoryImpl(gateway)
    private val useCase = GetAllUserUseCaseImpl(repository)
    private var presenter = MainPresenterImpl(useCase)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        rvUsers.adapter = UserAdapter(emptyList())
        rvUsers.isAnimating
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
    override fun showErrorMessage(errorMessage: String) {
        AlertDialog.Builder(this).setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK", null)
            .show()
    }

}

