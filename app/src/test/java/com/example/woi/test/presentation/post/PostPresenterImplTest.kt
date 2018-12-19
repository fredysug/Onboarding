package com.example.woi.test.presentation.post

import com.example.woi.test.domain.GetAllPostUseCase
import com.example.woi.test.utils.generatePost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PostPresenterImplTest {

    lateinit var getAllPostUseCase: GetAllPostUseCase
    lateinit var view: PostView
    private lateinit var presenter: PostPresenterImpl

    @Before
    fun setup() {
        getAllPostUseCase = mock()
        view = mock()
        presenter = PostPresenterImpl(getAllPostUseCase)
    }

    @Test
    fun testAttachView() {
        presenter.attach(view)
        Assert.assertNotNull(presenter.postView)
    }

    @Test
    fun testLoadPost() {
        val userId = "1"
        val expectedPost = generatePost(userId.toInt(), 5)

        whenever(getAllPostUseCase.execute(userId)).thenReturn(Single.just(expectedPost))

        presenter.attach(view)
        presenter.loadPost(userId)

        verify(view).showPost(expectedPost)
    }

    @Test
    fun testErrorLoadPost() {
        val userId = "1"
        val errorMessage = "Internal Server Error"
        whenever(getAllPostUseCase.execute(userId)).thenReturn(Single.error(Throwable(errorMessage)))

        presenter.attach(view)
        presenter.loadPost(userId)

        verify(view).error(errorMessage)
    }

    @Test
    fun testDetachView() {
        presenter.detach()
        Assert.assertTrue(presenter.disposeOnDetach.isDisposed)
    }

}