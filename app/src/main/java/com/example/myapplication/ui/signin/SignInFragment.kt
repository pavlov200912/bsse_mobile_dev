package com.example.myapplication.ui.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignInBinding
import com.example.myapplication.ui.base.BaseFragment
import dev.chrisbanes.insetter.applyInsetter

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    val viewModel: SignInViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackButtonPressed()
                }
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.signInButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        viewBinding.backButton.setOnClickListener {
            onBackButtonPressed()
        }
        viewBinding.signInButton.setOnClickListener {
            viewModel.signIn(
                viewBinding.emailEditText.text?.toString() ?: "",
                viewBinding.passwordEditText.text?.toString() ?: ""
            )
        }
        subscribeToFormFields()
    }

    private fun onBackButtonPressed() {
        val email = viewBinding.emailEditText.text?.toString() ?: ""
        val password = viewBinding.passwordEditText.text?.toString() ?: ""
        if (email.isBlank() && password.isBlank()) {
            findNavController().popBackStack() // TODO: hide keyboard
            return
        }
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.common_back_alert_dialog_text)
            .setNegativeButton(R.string.common_back_alert_dialog_cancel_button_text) { dialog, _ ->
                dialog?.dismiss()
            }
            .setPositiveButton(R.string.common_back_alert_dialog_ok_button_text) { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }

    private fun subscribeToFormFields() {
        decideSignInButtonEnabledState(
            viewBinding.emailEditText.text?.toString(),
            viewBinding.passwordEditText.text?.toString()
        )
        viewBinding.emailEditText.doAfterTextChanged { email ->
            decideSignInButtonEnabledState(
                email?.toString(),
                viewBinding.passwordEditText.text?.toString()
            )
        }
        viewBinding.passwordEditText.doAfterTextChanged { password ->
            decideSignInButtonEnabledState(
                viewBinding.emailEditText.text?.toString(),
                password?.toString()
            )
        }
    }

    private fun decideSignInButtonEnabledState(email: String?, password: String?) {
        viewBinding.signInButton.isEnabled = !(email.isNullOrBlank() || password.isNullOrBlank())
    }
}