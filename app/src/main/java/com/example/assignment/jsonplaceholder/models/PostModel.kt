package com.example.assignment.jsonplaceholder.models

import io.reactivex.Completable

interface PostModel {
    fun deletePost(): Completable
}