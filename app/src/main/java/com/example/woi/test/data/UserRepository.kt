package com.example.woi.test.data

import com.example.woi.test.data.remote.RemoteGateway
import com.example.woi.test.utils.Comment
import com.example.woi.test.utils.Post
import com.example.woi.test.utils.User
import io.reactivex.Single

interface UserRepository {
    fun getUsers(): Single<List<User>>
    fun getPosts(userId: String): Single<List<Post>>
    fun getComments(postId: String): Single<List<Comment>>
}

class UserRepositoryImpl(
    private val remoteGateway: RemoteGateway
) : UserRepository {

    override fun getPosts(userId: String): Single<List<Post>> = remoteGateway.getPosts(userId)
    override fun getComments(postId: String): Single<List<Comment>> = remoteGateway.getComments(postId)
    override fun getUsers(): Single<List<User>> = remoteGateway.getUsers()
}