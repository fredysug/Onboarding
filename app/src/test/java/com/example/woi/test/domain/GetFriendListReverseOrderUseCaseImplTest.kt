package com.example.woi.test.domain

import com.example.woi.test.data.UserRepository
import com.example.woi.test.utils.User
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test
import java.io.IOException

class GetFriendListReverseOrderUseCaseImplTest {

//    @Test
//    fun testReverseOrder() {
//        val repository: UserRepository = mock {
//            on { getFriendList() }.thenReturn(Single.just(getFriendListInOrder()))
//        }
//        val useCase = GetFriendListReverseOrderUseCaseImpl(repository)
//        val expectedReversedList = getFriendListInOrder().sortedByDescending { it.name}
//
//        useCase.execute().test().assertValue(expectedReversedList)
//    }
//
//    @Test
//    fun testFailedData() {
//        val repository: UserRepository = mock {
//            on { getFriendList() }.thenReturn(Single.error(ServerErrorException))
//        }
//        val useCase = GetFriendListReverseOrderUseCaseImpl(repository)
//
//        useCase.execute().test().assertError(ServerErrorException::class.java)
//            .assertErrorMessage("errorCode : 501, message: server maintained")
//
//    }


    private fun getFriendListInOrder() = (1..10).map {
        User()
    }


}

sealed class AppException(code: String,msg: String) : Throwable("errorCode : $code, message: $msg")

object UnauthorizedException : AppException("404","User unauthorized")
object ServerErrorException : AppException("501","server maintained")