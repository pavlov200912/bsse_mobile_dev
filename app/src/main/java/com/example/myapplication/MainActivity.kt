package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = "HelloWorld"
    }

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setupRecyclerView()
//        Log.d(LOG_TAG, "onCreate")
//
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.viewState.collect {
//                    viewState ->
//                    Log.d(LOG_TAG, viewState.toString())
//                    renderViewState(viewState)
//                }
//
//            }
//        }
    }

//    private fun renderViewState(viewState: MainViewModel.ViewState) {
//        when (viewState) {
//            is MainViewModel.ViewState.Loading -> {
//                viewBinding.userRecyclerView.isVisible = false
//                viewBinding.progressBar.isVisible = true
//            }
//            is MainViewModel.ViewState.Data -> {
//                viewBinding.userRecyclerView.isVisible = true
//                (viewBinding.userRecyclerView.adapter as UserAdapter).apply {
//                    userList = viewState.userList
//                    notifyDataSetChanged()
//                }
//                viewBinding.progressBar.isVisible = false
//            }
//        }
//    }
//
//    private fun setupRecyclerView(): UserAdapter {
//        val recyclerView = findViewById<RecyclerView>(R.id.userRecyclerView)
//        val adapter = UserAdapter()
//        recyclerView.adapter = adapter
//        return adapter
//    }
}