package com.example.assignment.posts.models

import com.example.assignment.api.PostsRetrofitService
import io.reactivex.Completable

class DefaultPostModel(
    private val apiService: PostsRetrofitService
) : PostModel {
    override fun deletePost(): Completable {
        return apiService.deletePost("1")
    }

    override fun getTextToSplit(): String {
        return "First/Second/Third"
    }
}