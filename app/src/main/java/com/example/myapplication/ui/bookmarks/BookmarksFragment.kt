package com.example.myapplication.ui.bookmarks

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBookmarksBinding
import com.example.myapplication.ui.base.BaseFragment

class BookmarksFragment : BaseFragment(R.layout.fragment_bookmarks) {
    val viewModel: BookmarksViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentBookmarksBinding::bind)
}