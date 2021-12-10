package com.example.myapplication.ui.news

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entity.Post
import com.example.myapplication.interactor.PostInteractor
import com.example.myapplication.ui.base.BaseViewModel
import com.example.myapplication.ui.userlist.UserListViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val postInteractor: PostInteractor
) : BaseViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState.asStateFlow()


    init {
        loadPosts()
    }


    private fun loadPosts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.d("load posts()")
                Thread.sleep(3000)

                _viewState.emit(ViewState.Loading)
                when (val response = postInteractor.loadPosts()) {
                    is NetworkResponse.Success -> {
                        _viewState.emit(ViewState.Data(response.body))
                    }
                    else -> {
                        Timber.d(response.toString())
                    }
                }
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val postList: List<Post>) : ViewState()
    }

}