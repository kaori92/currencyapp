package com.example.assignment.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        throw Error("Not available in free version")
    }
}
