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
import com.reem.jahez.base.BaseFragment
import com.reem.jahez.databinding.FragmentRegistrationBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch



@AndroidEntryPoint
class RegistrationFragment : BaseFragment() {

    private val registrationViewModel: RegistrationViewModel by viewModels()
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        setBaseViewModel(registrationViewModel)
        updateRegistrationState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            login.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
            registrationButton.setOnClickListener {
              clickRegistration()
            }
        }
    }

    private fun updateRegistrationState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.registrationUiState.collect {

                      if ( it ) findNavController().navigate(R.id.action_registrationFragment_to_restaurantsFragment)
                }
            }
        }
    }

    private fun setEditTextError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.validation.collect {
                    when(it.userName){
                        0 -> {
                            binding.usernameField.error = getString(R.string.required_field)
                        }
                        else -> {
                            binding.usernameField.error = null
                        }
                    }
                    when (it.email) {
                        0 -> {
                            binding.emailField.error = getString(R.string.required_field)
                        }
                        2 -> {
                            binding.emailField.error = getString(R.string.not_correct)
                        }
                        else -> {
                            binding.emailField.error = null
                        }
                    }
                    when (it.password) {
                        0 -> {
                            binding.passwordField.error = getString(R.string.required_field)
                        }
                        2 -> {
                            binding.passwordField.error = getString(R.string.not_correct)
                        }
                        else -> {
                            binding.passwordField.error = null
                        }
                    }
                }

            }
        }
    }
    private fun clickRegistration(){
        if (registrationViewModel.validation(
                binding.userName.text.toString(),
                binding.email.text.toString(),
                binding.password.text.toString()
        )){
            registrationViewModel.createNewUser(  binding.userName.text.toString(),
                binding.email.text.toString(),
                binding.password.text.toString() )
        }else{
            setEditTextError()
        }
    }

}