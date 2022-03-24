package com.reem.jahez.ui.registration

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
import com.reem.jahez.R
import com.reem.jahez.databinding.FragmentRegistrationBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val registrationViewModel: RegistrationViewModel by viewModels()
   var _binding :FragmentRegistrationBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun updateRegistrationState(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                registrationViewModel.registrationUiState.collect{
                    when {
                        it.isLoading ->  binding.loading.visibility = View.VISIBLE
                        it.message.isNotEmpty() -> {
                            binding.loading.visibility = View.GONE
                            binding.registerMessage.visibility = View.VISIBLE
                        }
                        it.data != null-> findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}