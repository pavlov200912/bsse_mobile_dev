package com.example.myapplication.interactor

import com.example.myapplication.entity.Post
import com.example.myapplication.repository.PostRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class PostInteractor @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun loadPosts(): NetworkResponse<List<Post>, Unit> =
        postRepository.getPosts()
}