package com.example.assignment.posts.models

import io.reactivex.Completable

interface PostModel {
    fun deletePost(): Completable

    fun getTextToSplit(): String
}