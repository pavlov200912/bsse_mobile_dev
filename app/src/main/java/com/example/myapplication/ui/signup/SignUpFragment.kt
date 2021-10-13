package com.example.myapplication.ui.signup

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignUpBinding
import com.example.myapplication.ui.base.BaseFragment

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    val viewModel: SignUpViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)
}