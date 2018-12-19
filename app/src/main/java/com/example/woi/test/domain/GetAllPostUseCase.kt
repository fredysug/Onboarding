package com.example.woi.test.domain

import com.example.woi.test.data.UserRepository
import com.example.woi.test.utils.Post
import io.reactivex.Single

interface GetAllPostUseCase {
    fun execute(userId: String): Single<List<Post>>
}

class GetAllPostUseCaseImpl(
    val repository: UserRepository
) : GetAllPostUseCase {
    override fun execute(userId: String): Single<List<Post>> {
        return repository.getPosts(userId)
    }

}