package com.example.woi.test.presentation.users

import com.example.woi.test.domain.GetAllUserUseCase
import com.example.woi.test.utils.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface UserView {
    fun showAllUser(users: List<User>)
    fun showErrorMessage(errorMessage: String)
    fun openPost(userId: String)
}

interface UserPresenter {
    fun attach(view: UserView)
    fun detach()
    fun loadUsers()
    fun openPost(userId: String)
}

class UserPresenterImpl(
    private val getAllUserUseCase: GetAllUserUseCase
) : UserPresenter {

    lateinit var view: UserView
    var disposable = CompositeDisposable()

    override fun attach(view: UserView) {
        this.view = view
    }

    override fun detach() {
        disposable.dispose()
    }

    override fun loadUsers() {
        getAllUserUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                disposable.add(it)
            }
            .subscribe({
                view.showAllUser(it)
            }, {
                view.showErrorMessage(it.message ?: "Unknown Error")
            })
    }

    override fun openPost(userId: String){
        view.openPost(userId)
    }
}