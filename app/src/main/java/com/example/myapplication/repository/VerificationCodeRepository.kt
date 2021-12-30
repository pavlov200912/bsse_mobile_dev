package com.example.myapplication.repository

import com.example.myapplication.data.net.Api
import com.example.myapplication.data.net.response.VerificationTokenResponse
import com.example.myapplication.data.net.response.error.SendRegistrationVerificationCodeErrorResponse
import com.example.myapplication.data.net.response.error.VerifyRegistrationCodeErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject
import dagger.Lazy

class VerificationCodeRepository @Inject constructor(
    private val apiLazy: Lazy<Api>,
) {
    private val api by lazy { apiLazy.get() }

    suspend fun sendVerificationCodeToEmail(
        email: String
    ): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return api.sendRegistrationVerificationCode(email)
    }

    suspend fun verifyEmailRegistrationCode(
        code: String,
        email: String
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        return api.verifyRegistrationCode(code, email)
    }
}