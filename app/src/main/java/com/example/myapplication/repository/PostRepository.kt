package com.example.myapplication.repository

import com.example.myapplication.data.net.Api
import com.example.myapplication.data.persistent.LocalKeyValueStorage
import com.example.myapplication.di.AppCoroutineScope
import com.example.myapplication.di.IoCoroutineDispatcher
import com.example.myapplication.entity.Post
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val apiLazy: Lazy<Api>,
    @AppCoroutineScope externalCoroutineScope: CoroutineScope,
    @IoCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val api by lazy { apiLazy.get() }

    suspend fun getPosts() : NetworkResponse<List<Post>, Unit> {
        return api.getPosts()
    }
}