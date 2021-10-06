package com.example.myapplication

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.databinding.FragmentSignUpBinding

class SignUpFragment: BaseFragment(R.layout.fragment_sign_up) {
    val viewModel: SignUpViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)
}