package com.example.myapplication.ui.userlist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.FragmentUserlistBinding
import com.example.myapplication.ui.MainViewModel
import com.example.myapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : BaseFragment(R.layout.fragment_userlist) {
    private lateinit var viewModel: UserListViewModel
    private val viewBinding by viewBinding(FragmentUserlistBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToViewState()
        viewBinding.usersRecyclerView.applyInsetter {
            type(statusBars = true) { margin() }
        }

        viewBinding.refreshButton.setOnClickListener {
            viewModel.loadUsers()
        }

    }

    private fun subscribeToViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState -> renderViewState(viewState) }
            }
        }
    }

    private fun setupRecyclerView(): UserAdapter = UserAdapter().also {
        viewBinding.usersRecyclerView.adapter = it
    }

    private fun renderViewState(viewState: UserListViewModel.ViewState) {
        when (viewState) {
            is UserListViewModel.ViewState.Loading -> {
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = true

                viewBinding.refreshButton.isVisible = false
                viewBinding.errorText.isVisible = false
            }
            is UserListViewModel.ViewState.Data -> {
                viewBinding.usersRecyclerView.isVisible = true
                (viewBinding.usersRecyclerView.adapter as UserAdapter).apply {
                    userList = viewState.userList
                    notifyDataSetChanged()
                }
                viewBinding.progressBar.isVisible = false

                viewBinding.refreshButton.isVisible = false
                viewBinding.errorText.isVisible = false
            }
            is UserListViewModel.ViewState.Error -> {
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = false

                viewBinding.refreshButton.isVisible = true
                viewBinding.errorText.isVisible = true
                viewBinding.errorText.text = resources.getString(R.string.user_list_error)
            }
            is UserListViewModel.ViewState.EmptyList -> {
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = false

                viewBinding.refreshButton.isVisible = true
                viewBinding.errorText.isVisible = true
                viewBinding.errorText.text = resources.getString(R.string.user_list_empty)
            }
        }
    }
}