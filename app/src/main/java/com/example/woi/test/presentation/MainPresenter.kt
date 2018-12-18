package com.example.woi.test.presentation

import com.example.woi.test.domain.GetAllUserUseCase
import com.example.woi.test.utils.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface View {
    fun showAllUser(users: List<User>)
    fun showErrorMessage(errorMessage: String)
}

interface MainPresenter {
    fun attach(view: View)
    fun detach()
    fun loadUsers()
}

class MainPresenterImpl(
    private val getAllUserUseCase: GetAllUserUseCase
) : MainPresenter {

    lateinit var view: View
    var disposable = CompositeDisposable()

    override fun attach(view: View) {
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
}