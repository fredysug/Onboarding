package com.example.woi.test.data.remote

import com.example.woi.test.utils.Comment
import com.example.woi.test.utils.Post
import com.example.woi.test.utils.User
import com.example.woi.test.utils.api
import io.reactivex.Single
import retrofit2.HttpException

interface RemoteGateway {
    fun getUsers(): Single<List<User>>
    fun getPosts(id: String): Single<List<Post>>
    fun getComments(id: String): Single<List<Comment>>
}

class RemoteGatewayImpl : RemoteGateway {
    override fun getUsers(): Single<List<User>> {
        return api.getUsers()
    }

    override fun getPosts(id: String): Single<List<Post>> {
        return api.getPosts(id.toInt()).onErrorResumeNext {
            Single.error(handleError(it))
        }
    }

    override fun getComments(id: String): Single<List<Comment>> {
        return api.getComments(id.toInt())
    }

    private fun handleError(error: Throwable) = when (error) {
        is HttpException -> try {
            ErrorServer(error.code(), error.message())
        } catch (e: Throwable) {
            Throwable(e.message)
        }
        else -> Throwable(error)
    }
}

class ErrorServer(errorCode: Int, errorMessage: String) : Exception("$errorCode : $errorMessage")
