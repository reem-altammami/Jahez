package com.reem.jahez.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reem.jahez.R
import com.reem.jahez.databinding.FragmentLoginBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
if(Firebase.auth.currentUser?.uid?.isNotEmpty() == true){
    findNavController().navigate(R.id.action_loginFragment_to_restaurantsFragment)
}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        updateLoginState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        binding.loginButton.setOnClickListener {
     clickLogin()
        }
    }

    private fun updateLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginUiState.collect {
                    when {
                        it.isLoading -> binding.loading.visibility = View.VISIBLE
                        it.data != null -> findNavController().navigate(R.id.action_loginFragment_to_restaurantsFragment)
                        it.message.isNotEmpty() -> {
                            binding.loading.visibility = View.GONE
                            binding.loginMessage.visibility = View.VISIBLE
                            binding.loginMessage.text = it.message
                        }
                    }
                }
            }
        }
    }

    private fun setEditTextError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.validation.collect {
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

    private fun clickLogin(){
        if( loginViewModel.validation(
                binding.email.text.toString(),
                binding.password.text.toString()
            )){
            loginViewModel.login(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
        } else{
            setEditTextError()
        }
    }
}