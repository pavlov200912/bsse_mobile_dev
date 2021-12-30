package com.example.myapplication.ui.emailconfirmation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEmailConfirmationBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by hiltNavGraphViewModels(R.id.signUpFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signUpForm = signUpViewModel.signUpData ?: error("Sign up data not set up")

        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.openEmailButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.confirmButton.setOnClickListener {
            viewModel.signUpWithCode(
                signUpForm.firstname,
                signUpForm.lastname,
                signUpForm.nickname,
                signUpForm.email,
                signUpForm.password,
                viewBinding.verificationCodeEditText.getCode()
            )
        }

        // open email app
        viewBinding.openEmailButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_MAIN
                ).apply {
                    addCategory(Intent.CATEGORY_APP_EMAIL)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }

        subscribeToFormFields()
        subscribeToEvents()
        subscribeToTimer()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sendVerificationCode(signUpForm.email)
        }
    }

    private fun subscribeToFormFields() {
        viewBinding.confirmButton.isEnabled = false
        viewBinding.verificationCodeEditText.onVerificationCodeFilledChangeListener = {
            viewBinding.confirmButton.isEnabled = it
        }
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emailConfirmationActionStateFlow().collect { event ->
                    when (event) {
                        EmailConfirmationViewModel.EmailConfirmationActionState.Loading -> {
                            // nothing
                        }
                        EmailConfirmationViewModel.EmailConfirmationActionState.Pending -> {
                            // nothing
                        }
                        is EmailConfirmationViewModel.EmailConfirmationActionState.NetworkError ->
                            Timber.d(event.e.toString())
                        is EmailConfirmationViewModel.EmailConfirmationActionState.ServerError ->
                            Timber.d(event.e.toString())
                        is EmailConfirmationViewModel.EmailConfirmationActionState.EmailVerificationError ->
                            Timber.d(event.e.toString())
                        is EmailConfirmationViewModel.EmailConfirmationActionState.UnknownError ->
                            Timber.d(event.e.toString())

                    }
                }
            }
        }
    }

    private fun subscribeToTimer() {
        viewBinding.sendCode.setOnClickListener {
            viewModel.viewModelScope.launch {
                Toast.makeText(
                    requireContext(),
                    "Секунду, присылаем повторный код",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.sendVerificationCode(
                    (signUpViewModel.signUpData ?: error("signUpData error")).email
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timerStateFlow.collect { remainingMs ->
                    viewBinding.sendCode.isEnabled = remainingMs == 0L
                }
            }
        }
    }

}