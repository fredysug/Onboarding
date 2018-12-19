package com.example.woi.test.presentation.post

import com.example.woi.test.domain.GetAllPostUseCase
import com.example.woi.test.utils.Post
import io.reactivex.disposables.CompositeDisposable

interface PostView {
    fun showPost(posts: List<Post>)
    fun error(errorMessage: String)
}

interface PostPresenter {
    fun attach(postView: PostView)
    fun detach()
    fun loadPost(userId: String)
}

class PostPresenterImpl(
    val getAllPostUseCase: GetAllPostUseCase
) : PostPresenter {

    lateinit var postView: PostView
    val disposeOnDetach = CompositeDisposable()

    override fun attach(postView: PostView) {
        this.postView = postView
    }

    override fun detach() {
        disposeOnDetach.dispose()
    }

    override fun loadPost(userId: String) {
        getAllPostUseCase.execute(userId).subscribe({
            postView.showPost(it)
        }, {
            postView.error(it.message?: "Unkown error")
        }).let {
            disposeOnDetach.add(it)
        }
    }

}