package com.example.myapplication.repository

import com.example.myapplication.data.net.Api
import com.example.myapplication.entity.User
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: Api
) {
    suspend fun getUsers(): NetworkResponse<List<User>, Unit> =
        api.getUsers()
}