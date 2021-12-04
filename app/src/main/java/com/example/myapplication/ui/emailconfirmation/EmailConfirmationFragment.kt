package com.example.myapplication.ui.emailconfirmation

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEmailConfirmationBinding
import com.example.myapplication.ui.base.BaseFragment

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    val viewModel: EmailConfirmationViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)
}