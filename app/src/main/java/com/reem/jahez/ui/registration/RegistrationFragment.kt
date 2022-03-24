package com.reem.jahez.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.reem.jahez.R
import com.reem.jahez.databinding.FragmentRegistrationBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch



@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val registrationViewModel: RegistrationViewModel by viewModels()
    var _binding: FragmentRegistrationBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        updateRegistrationState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registrationButton.setOnClickListener {
                if (setEmailEditTextError() && setPasswordEditTextError() && setUsernameEditTextError()) {
                    registrationViewModel.createNewUser(
                        binding.userName.text.toString(),
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    )
//                    updateRegistrationState()
                } else {
                    setEmailEditTextError()
                    setPasswordEditTextError()
                    setUsernameEditTextError()
                }
            }
        }
    }

    fun updateRegistrationState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.registrationUiState.collect {
                    when {
                        it.isLoading -> {
                            binding.loading.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }
                        it.message.isNotEmpty() -> {
                            binding.loading.visibility = View.GONE
                            binding.registerMessage.visibility = View.VISIBLE
                        }
                        it.data != null -> findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    }
                }
            }
        }
    }

    private fun validUseName(): Boolean =
        binding.userName.text!!.isNotEmpty()

    private fun validEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()


    fun validPassword(): Boolean =
        binding.password.text.toString().isNotEmpty() && binding.password.text.toString()
            .length>=6

    private fun setUsernameEditTextError(): Boolean {
        return if (binding.userName.text.toString().isEmpty()) {
            binding.usernameField.error = getString(R.string.required_field)
            false
        } else {
            if (!validUseName()) {
                binding.usernameField.error = getString(R.string.not_correct)
                false
            } else {
                binding.usernameField.error = null
                true
            }
        }
    }

    private fun setEmailEditTextError(): Boolean {
        return if (binding.email.text.toString().isEmpty()) {
            binding.emailField.error = "Required field"
            false
        } else {
            if (!validEmail()) {
                binding.emailField.error = "Not correct"
                false
            } else {
                binding.emailField.error = null
                true
            }
        }
    }

    private fun setPasswordEditTextError(): Boolean {
        return if (binding.password.text.toString().isEmpty()) {
            binding.passwordField.error = "Required field"
            false
        } else {
            if (!validPassword()) {
                binding.passwordField.error = "Not correct"
                false
            } else {
                binding.passwordField.error = null
                true
            }
        }
    }

}