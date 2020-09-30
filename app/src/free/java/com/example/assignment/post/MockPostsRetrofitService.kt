package com.example.assignment.post

import com.example.assignment.api.PostsRetrofitService
import io.reactivex.Completable

class MockPostsRetrofitService: PostsRetrofitService {

    override fun deletePost(postNumber: String): Completable {
        return Completable.complete()
    }
}