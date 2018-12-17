package com.example.woi.test.data

import io.reactivex.Single

interface UserRepository {
    fun getUser(): User
    fun getFriendList(): Single<List<User>>
}

class UserRepositoryImpl : UserRepository {
    override fun getFriendList(): Single<List<User>> {
        return Single.just((1..10).map {
            User(it.toString(), "user-$it")
        })
    }

    override fun getUser(): User {
        return User("randomUser", "avatarUrl")
    }
}

data class User(
    val userName: String,
    val avatar: String
)