package com.example.myapplication.data.net.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInWithEmailRequest(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)