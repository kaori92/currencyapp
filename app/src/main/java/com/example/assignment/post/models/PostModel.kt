package com.example.assignment.post.models

import io.reactivex.Completable

interface PostModel {
    fun deletePost(): Completable

    fun getTextToSplit(): String
}