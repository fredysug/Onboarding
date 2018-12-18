package com.example.woi.test.data

import com.example.woi.test.data.remote.RemoteGateway
import com.example.woi.test.utils.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private lateinit var repo: UserRepository
    private lateinit var gateway: RemoteGateway

    @Before
    fun setup() {
        gateway = mock()
        repo = UserRepositoryImpl(gateway)
    }

    @Test
    fun testGetUsers() {
        val expectedUserCount = 20
        whenever(gateway.getUsers()).thenReturn(Single.just(generateUser(expectedUserCount)))

        repo.getUsers().test().assertValue {
            it.size == expectedUserCount
        }

        verify(gateway).getUsers()
    }

    @Test
    fun testGetPosts() {
        val userId = 1
        val expectedPostCount = 10
        val generatedPost = generatePost(userId, expectedPostCount)

        whenever(gateway.getPosts(userId.toString())).thenReturn(Single.just(generatedPost))

        repo.getPosts(userId.toString()).test().assertValue {
            it.size == expectedPostCount
        }
    }

    @Test
    fun testGetComments() {
        val postId = 1
        val expectedPostCount = 10
        val generatedComment = generateComment(postId, expectedPostCount)

        whenever(gateway.getComments(postId.toString())).thenReturn(Single.just(generatedComment))

        repo.getComments(postId.toString()).test().assertValue {
            it.size == expectedPostCount
        }
    }

}