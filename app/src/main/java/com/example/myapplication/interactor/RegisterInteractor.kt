package com.example.myapplication.interactor

import android.os.CountDownTimer
import com.example.myapplication.data.net.response.VerificationTokenResponse
import com.example.myapplication.data.net.response.error.SendRegistrationVerificationCodeErrorResponse
import com.example.myapplication.data.net.response.error.VerifyRegistrationCodeErrorResponse
import com.example.myapplication.repository.VerificationCodeRepository
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class RegisterInteractor @Inject constructor(
    private val verificationCodeRepository: VerificationCodeRepository
) {

    private val timerFlow = MutableStateFlow<Long>(0)

    private var timer: CountDownTimer? = null

    fun timerFlow(): Flow<Long> = timerFlow.asStateFlow()

    suspend fun sendVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return _sendVerificationCode(email)
    }

    private suspend fun _sendVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return verificationCodeRepository.sendVerificationCodeToEmail(email)
    }

    suspend fun verifyEmail(
        email: String,
        code: String
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        return verificationCodeRepository.verifyEmailRegistrationCode(code, email)
    }
}