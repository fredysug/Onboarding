package com.example.woi.test.domain

import com.example.woi.test.data.UserRepository
import com.example.woi.test.utils.generateUser
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetAllUserUseCaseImplTest {

    lateinit var userRepository: UserRepository
    lateinit var useCase: GetAllUserUseCase

    @Before
    fun setup() {
        userRepository = mock()
        useCase = GetAllUserUseCaseImpl(userRepository)
    }

    @Test
    fun testGetAllUserShouldReturnListOfUser() {
        val expectedTotalUser = 10
        val expectedResult = generateUser(expectedTotalUser)

        whenever(userRepository.getUsers()).thenReturn(Single.just(expectedResult))

        useCase.execute().test().assertValue {
            it.size == expectedTotalUser &&
                    it == expectedResult
        }
    }

    @Test
    fun testGetAllUserShouldShowError() {
        val expectedErrorMessage = "Internal Server Error"
        whenever(userRepository.getUsers()).thenReturn(Single.error(Throwable(expectedErrorMessage)))
        useCase.execute().test().assertErrorMessage(expectedErrorMessage)
    }
}