package com.example.myapplication.ui.likes

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLikesBinding
import com.example.myapplication.ui.base.BaseFragment

class LikesFragment : BaseFragment(R.layout.fragment_likes) {
    val viewModel: LikesViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentLikesBinding::bind)
}