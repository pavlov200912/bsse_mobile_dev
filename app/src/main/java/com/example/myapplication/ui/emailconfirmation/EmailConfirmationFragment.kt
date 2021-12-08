package com.example.myapplication.ui.emailconfirmation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEmailConfirmationBinding
import com.example.myapplication.ui.base.BaseFragment
import dev.chrisbanes.insetter.applyInsetter

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    val viewModel: EmailConfirmationViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.openEmailButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}