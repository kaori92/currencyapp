package com.example.assignment.api

import io.reactivex.Completable
import retrofit2.http.DELETE
import retrofit2.http.Path

interface PostsRetrofitService {

    @DELETE("posts/{post}")
    fun deletePost(@Path("post") postNumber: String): Completable
}