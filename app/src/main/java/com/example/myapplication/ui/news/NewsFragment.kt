package com.example.myapplication.ui.news

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFragment : BaseFragment(R.layout.fragment_news) {

    val viewModel: NewsViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentNewsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        subscribeToViewState()
        viewBinding.toolbar.applyInsetter {
            type(statusBars = true) { margin() }
        }
    }

    private fun subscribeToViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState -> renderViewState(viewState) }
            }
        }
    }

    private fun renderViewState(viewState: NewsViewModel.ViewState) {
        when (viewState) {
            is NewsViewModel.ViewState.Loading -> {
                viewBinding.newsRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = true

            }
            is NewsViewModel.ViewState.Data -> {
                viewBinding.newsRecyclerView.isVisible = true
                (viewBinding.newsRecyclerView.adapter as NewsAdapter).apply {
                    postList = viewState.postList
                    notifyDataSetChanged()
                }
                viewBinding.progressBar.isVisible = false

            }
        }
    }

    private fun setupRecyclerView(): NewsAdapter = NewsAdapter().also {
        viewBinding.newsRecyclerView.adapter = it
    }
}