package com.example.myapplication

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.databinding.FragmentEmailConfirmationBinding

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    val viewModel: EmailConfirmationViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)
}