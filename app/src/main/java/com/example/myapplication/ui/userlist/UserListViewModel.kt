package com.example.myapplication.ui.userlist

import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.net.Api
import com.example.myapplication.data.net.MockApi
import com.example.myapplication.entity.User
import com.example.myapplication.interactor.UsersInteractor
import com.example.myapplication.ui.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val usersInteractor: UsersInteractor
) : BaseViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState.asStateFlow()


    init {
        loadUsers()
    }


    private fun loadUsers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.d("load users()")
                Thread.sleep(3000)

                _viewState.emit(ViewState.Loading)
                when (val response = usersInteractor.loadUsers()) {
                    is NetworkResponse.Success -> {
                        _viewState.emit(ViewState.Data(response.body))
                    }
                    else -> {

                    }
                }
            }
        }
    }


    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val userList: List<User>) : ViewState()
    }

}