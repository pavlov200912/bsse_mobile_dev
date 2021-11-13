package com.example.myapplication

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.databinding.FragmentMainBinding

class MainFragment: BaseFragment(R.layout.fragment_main) {
    // todo val viewModel: MAinViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentMainBinding::bind)
}