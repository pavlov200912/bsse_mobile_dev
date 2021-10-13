package com.example.myapplication.data.net

import com.example.myapplication.entity.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET

interface Api {

    @GET("users")
    suspend fun getUsers(): GetUsersResponse
}

@JsonClass(generateAdapter = true)
data class GetUsersResponse(
    @Json(name = "data") val data: List<User>
)