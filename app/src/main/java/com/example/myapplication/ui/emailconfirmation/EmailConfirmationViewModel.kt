package com.example.myapplication.ui.emailconfirmation

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.net.response.error.CreateProfileErrorResponse
import com.example.myapplication.data.net.response.error.SendRegistrationVerificationCodeErrorResponse
import com.example.myapplication.data.net.response.error.VerifyRegistrationCodeErrorResponse
import com.example.myapplication.interactor.AuthInteractor
import com.example.myapplication.interactor.RegisterInteractor
import com.example.myapplication.ui.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val registerWithEmailInteractor: RegisterInteractor
) :
    BaseViewModel() {

    private val TIMEOUT = 15L
    private var timer: CountDownTimer? = null
    private val _timerStateFlow = MutableStateFlow(0L)
    val timerStateFlow = _timerStateFlow.asStateFlow()


    private val _emailConfirmationActionStateFlow =
        MutableStateFlow<EmailConfirmationActionState>(
            EmailConfirmationActionState.Pending
        )

    fun emailConfirmationActionStateFlow(): Flow<EmailConfirmationActionState> {
        return _emailConfirmationActionStateFlow.asStateFlow()
    }

    suspend fun sendVerificationCode(email: String) {
        _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Loading)
        when (val response = registerWithEmailInteractor.sendVerificationCode(email)) {
            is NetworkResponse.Success -> {
                _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Pending)
                timer?.cancel() // if there is a timer, shut it down - we have a code already
                timer = object : CountDownTimer(TIMEOUT * 1000, 150L) {
                    override fun onTick(leftMs: Long) {
                        viewModelScope.launch {
                            _timerStateFlow.emit(leftMs)
                        }
                    }

                    override fun onFinish() {
                        viewModelScope.launch {
                            _timerStateFlow.emit(0)
                        }
                    }
                }.start()
            }
            is NetworkResponse.ServerError -> {
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.SendVerificationCodeError(
                        response
                    )
                )
            }
            is NetworkResponse.NetworkError -> {
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.NetworkError(
                        response
                    )
                )
            }
            is NetworkResponse.UnknownError -> {
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.UnknownError(
                        response
                    )
                )
            }
        }
    }

    fun signUpWithCode(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String,
        code: String
    ) {
        viewModelScope.launch {
            _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Loading)
            try {
                val token: String
                when (val response = registerWithEmailInteractor.verifyEmail(email, code)) {
                    is NetworkResponse.Success -> {
                        token = response.body.verificationToken
                    }
                    is NetworkResponse.ServerError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.EmailVerificationError(
                                response
                            )
                        )
                        return@launch
                    }
                    is NetworkResponse.NetworkError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.NetworkError(
                                response
                            )
                        )
                        return@launch
                    }
                    is NetworkResponse.UnknownError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.UnknownError(
                                response
                            )
                        )
                        return@launch
                    }
                }
                when (val result = authInteractor.signUpWithEmailAndInfo(
                    firstname,
                    lastname,
                    nickname,
                    email,
                    password,
                    token
                )) {
                    is NetworkResponse.Success<*> -> {
                        _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Pending)
                    }
                    is NetworkResponse.ServerError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.ServerError(
                                result
                            )
                        )
                    }
                    is NetworkResponse.NetworkError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.NetworkError(
                                result
                            )
                        )
                    }
                    is NetworkResponse.UnknownError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.UnknownError(
                                result
                            )
                        )
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.UnknownError(
                        NetworkResponse.UnknownError(error)
                    )
                )
            }
        }
    }

    sealed class EmailConfirmationActionState {
        object Pending : EmailConfirmationActionState()
        object Loading : EmailConfirmationActionState()
        data class ServerError(val e: NetworkResponse.ServerError<CreateProfileErrorResponse>) :
            EmailConfirmationActionState()

        data class SendVerificationCodeError(val e: NetworkResponse.ServerError<SendRegistrationVerificationCodeErrorResponse>) :
            EmailConfirmationActionState()

        data class EmailVerificationError(val e: NetworkResponse.ServerError<VerifyRegistrationCodeErrorResponse>) :
            EmailConfirmationActionState()

        data class NetworkError(val e: NetworkResponse.NetworkError) :
            EmailConfirmationActionState()

        data class UnknownError(val e: NetworkResponse.UnknownError) :
            EmailConfirmationActionState()
    }
}