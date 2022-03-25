package com.reem.jahez.ui.restaurants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.reem.jahez.databinding.FragmentRestaurantsBinding
import com.reem.jahez.ui.bindRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantsFragment : Fragment() {
  private var _binding : FragmentRestaurantsBinding? = null
private val binding get() = _binding!!
    private val restaurantsViewModel : RestaurantsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRestaurantsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.restaurantsViewModel=restaurantsViewModel
        binding.recyclerView.adapter = RestaurantsAdapter()
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                restaurantsViewModel.restaurantUi.collect{
                    when{
                        it.isLoading -> binding.loading.visibility =View.VISIBLE
                        it.restaurantsItemList !=null -> {
                            binding.loading.visibility = View.GONE
                            bindRecyclerView(binding.recyclerView, it.restaurantsItemList)
                        }
                        it.message.isNotEmpty() -> {
                        binding.loading.visibility = View.GONE
                        binding.errorMessage.visibility=View.VISIBLE
                        binding.errorMessage.text = it.message

                    }
                    }
                }
            }
        }
    }


}