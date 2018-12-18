package com.example.woi.test.utils

import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

val api = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .client(OkHttpClient())
    .addConverterFactory(GsonConverterFactory.create(Gson()))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(MockService::class.java)

interface MockService {

    @GET("/users")
    fun getUsers(): Single<List<User>>

    @GET("/posts/{id}")
    fun getPosts(@Path("id") postId: Int): Single<List<Post>>

    @GET("/comments?postId={id}")
    fun getComments(@Path("id") postId: Int): Single<List<Comment>>
}

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

data class User(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = ""
)