package com.example.woi.test.data

import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryImplTest {

    @Test
    fun testGetUser() {
        val repo = UserRepositoryImpl()

        val expectedUser = User("randomUser", "avatarUrl")
        assertEquals(expectedUser, repo.getUser())
    }

    @Test
    fun testGetFriendList() {
        val repo = UserRepositoryImpl()
        var friendList = (1..10).map {
            User(it.toString(), "user-$it")
        }

        assertEquals(friendList, repo.getFriendList())
    }
}