package com.example.assignment.jsonplaceholder.models

import com.example.assignment.api.PostsRetrofitService
import io.reactivex.Completable

class DefaultPostModel(
    private val apiService: PostsRetrofitService
) : PostModel {
    override fun deletePost(): Completable {
        return apiService.deletePost("1")
    }
}