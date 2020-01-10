package com.example.assignment.posts.models

import com.example.assignment.api.PostsRetrofitService
import com.example.assignment.core.JSON_API_URL
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class PostModelTest : Spek({
    lateinit var testObserver: TestObserver<Void>
    val apiService: PostsRetrofitService by memoized { mock<PostsRetrofitService>() }
    val model: PostModel by memoized {
        DefaultPostModel(apiService)
    }
    val postId = "1"

    describe("deleting post") {
        context("when delete succeeded") {
            beforeEachTest {
                given(apiService.deletePost(postId)).willReturn(Completable.complete())
                testObserver = model.deletePost().test()
            }

            it("should completable be completed") {
                testObserver.assertComplete()
            }
        }

        context("when delete failed") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host $JSON_API_URL: No address associated with hostname")

            beforeEachTest {
                given(apiService.deletePost(postId)).willReturn(Completable.error(error))
                testObserver = model.deletePost().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }
})