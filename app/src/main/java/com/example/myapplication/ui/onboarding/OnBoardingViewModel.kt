package com.example.myapplication.ui.onboarding

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entity.User
import com.example.myapplication.ui.base.BaseViewModel
import com.example.myapplication.ui.userlist.UserListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnBoardingViewModel: BaseViewModel() {
    var isVolume: Boolean = false
    var userTouchTime: Long = 0
    val delay: Long = 5000


    private val _viewState = MutableStateFlow<Long>(0)
    val viewState: Flow<Long> get() = _viewState.asStateFlow()

    init {
        userTouchTime = System.currentTimeMillis()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    Log.d("OnBoarding", "Running in cycle $userTouchTime")
                    if (System.currentTimeMillis() - userTouchTime > delay) {
                        Log.d("OnBoarding", "Delay passed")
                        userTouchTime = System.currentTimeMillis()
                        _viewState.emit(userTouchTime)
                    }
                    Thread.sleep(delay)
                }
            }
        }
    }

    sealed class ViewState {
        object Sleep : ViewState()
        object Scroll : ViewState()
    }
}