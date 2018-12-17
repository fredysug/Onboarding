package com.example.woi.test.domain

import com.example.woi.test.data.User
import com.example.woi.test.data.UserRepository
import io.reactivex.Single

interface GetFriendListReverseOrderUseCase {
    fun execute(): Single<List<User>>
}

class GetFriendListReverseOrderUseCaseImpl(private val userRepository: UserRepository) :
    GetFriendListReverseOrderUseCase {
    override fun execute(): Single<List<User>> {
        return userRepository.getFriendList().map {
            it.sortedByDescending { it.userName }
        }
    }

}