package com.example.woi.test.utils

fun generateUser(count: Int = 1) = (1..count).map {
    User(it)
}

fun generatePost(userId: Int, count: Int = 1) = (1..count).map {
    Post(userId, it, "title: $it", "body: $it")
}

fun generateComment(postId: Int, count: Int = 1) = (1..count).map {
    Comment(postId, it, "Name: $it", "Email: $it", "Body: $it")
}