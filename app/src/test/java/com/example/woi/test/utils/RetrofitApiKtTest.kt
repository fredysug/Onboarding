package com.example.woi.test.utils

import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

class RetrofitApiKtTest{

    lateinit var server: MockWebServer

    @Before
    fun setup(){
        server = MockWebServer()

    }


}