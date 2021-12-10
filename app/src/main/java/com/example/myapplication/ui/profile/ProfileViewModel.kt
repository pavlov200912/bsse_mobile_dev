package com.example.myapplication.ui.profile

import com.example.myapplication.ui.base.BaseViewModel

import androidx.lifecycle.viewModelScope
import com.example.myapplication.interactor.AuthInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
): BaseViewModel() {

    private val _eventChannel = Channel<Event>(Channel.BUFFERED)

    fun eventsFlow(): Flow<Event> {
        return _eventChannel.receiveAsFlow()
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
}