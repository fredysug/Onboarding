package com.example.woi.test.domain

import com.example.woi.test.data.UserRepository
import com.example.woi.test.utils.generatePost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GetAllPostUseCaseImplTest {

    @Test
    fun testGetAllPost() {
        val userId = "1"

        val repository: UserRepository = mock()
        val getAllPostUseCase = GetAllPostUseCaseImpl(repository)

        val expectedPost = generatePost(userId.toInt())
        whenever(repository.getPosts(userId)).thenReturn(Single.just(expectedPost))

        getAllPostUseCase.execute(userId).test().assertValue {
            it == expectedPost
        }
    }
}