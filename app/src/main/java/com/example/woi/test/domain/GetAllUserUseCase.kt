package com.example.woi.test.domain

import com.example.woi.test.data.UserRepository
import com.example.woi.test.utils.User
import io.reactivex.Single

interface GetAllUserUseCase{
    fun execute(): Single<List<User>>
}

class GetAllUserUseCaseImpl(
    private val userRepository: UserRepository
): GetAllUserUseCase{
    override fun execute(): Single<List<User>> {
        return userRepository.getUsers()
    }

}