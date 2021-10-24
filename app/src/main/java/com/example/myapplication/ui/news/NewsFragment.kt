package com.example.myapplication.ui.news

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.ui.base.BaseFragment

class NewsFragment : BaseFragment(R.layout.fragment_news) {
    val viewModel: NewsViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentNewsBinding::bind)
}