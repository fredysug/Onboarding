package com.example.woi.test.presentation

import com.example.woi.test.domain.GetAllUserUseCase
import com.example.woi.test.presentation.users.UserPresenterImpl
import com.example.woi.test.presentation.users.UserView
import com.example.woi.test.utils.generateUser
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class UserPresenterImplTest {
    private lateinit var getAllUserUseCase: GetAllUserUseCase
    private lateinit var userPresenter: UserPresenterImpl
    private lateinit var mainView: UserView

    @get:Rule
    val rule = TrampolineSchedulerRule()

    @Before
    fun setup() {
        getAllUserUseCase = mock()
        userPresenter = UserPresenterImpl(getAllUserUseCase)
        mainView = mock()
        userPresenter.attach(mainView)
    }

    @Test
    fun getAllUser() {
        val expectedTotalUser = 10
        val expectedResult = generateUser(expectedTotalUser)
        whenever(getAllUserUseCase.execute()).thenReturn(Single.just(expectedResult))

        userPresenter.loadUsers()

        verify(mainView).showAllUser(expectedResult)
    }

    @Test
    fun showError() {
        val expectedResult = "Internal Server Error"
        whenever(getAllUserUseCase.execute()).thenReturn(Single.error(Throwable(expectedResult)))

        userPresenter.loadUsers()

        verify(mainView).showErrorMessage(expectedResult)
    }

    @Test
    fun detachShouldRemoveView() {
        userPresenter.detach()
        assertTrue(userPresenter.disposable.isDisposed)
    }
}

class TrampolineSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}