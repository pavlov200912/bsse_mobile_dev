package com.example.myapplication.ui.profile

import com.example.myapplication.ui.base.BaseViewModel

import androidx.lifecycle.viewModelScope
import com.example.myapplication.entity.User
import com.example.myapplication.interactor.AuthInteractor
import com.example.myapplication.interactor.ProfileInteractor
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val profileInteractor: ProfileInteractor
): BaseViewModel() {

    private val _eventChannel = Channel<Event>(Channel.BUFFERED)
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState.asStateFlow()

    fun eventsFlow(): Flow<Event> {
        return _eventChannel.receiveAsFlow()
    }

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _viewState.emit(ViewState.Loading)
            when (val response = profileInteractor.getProfile()) {
                is NetworkResponse.Success -> {
                    _viewState.emit(ViewState.Data(response.body))
                }
                else -> {
                    // errors
                }
            }

        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authInteractor.logout()
            } catch (error: Throwable) {
                Timber.e(error)
                _eventChannel.send(Event.LogoutError(error))
            }
        }
    }

    sealed class Event {
        data class LogoutError(val error: Throwable) : Event()
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val user: User) : ViewState()
    }
}