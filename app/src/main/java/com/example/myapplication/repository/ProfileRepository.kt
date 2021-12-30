package com.example.myapplication.repository

import com.example.myapplication.data.net.Api
import com.example.myapplication.entity.User
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.Lazy
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val apiLazy: Lazy<Api>,
) {
    private val api by lazy { apiLazy.get() }

    suspend fun getProfile() : NetworkResponse<User, Unit> =
        api.getProfile()

}