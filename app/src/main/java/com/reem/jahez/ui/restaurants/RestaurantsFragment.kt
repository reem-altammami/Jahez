package com.reem.jahez.ui.restaurants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.reem.jahez.R
import com.reem.jahez.databinding.FragmentRestaurantsBinding
import com.reem.jahez.ui.bindRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.restaurant_item.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantsFragment : Fragment() {
    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!
    private val restaurantsViewModel: RestaurantsViewModel by viewModels()
    private var sort = ""
    private var filter = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.restaurantsViewModel = restaurantsViewModel
        binding.recyclerView.adapter = RestaurantsAdapter()
        binding.sort.setOnClickListener { showSortPopupMenu(binding.sort) }
        binding.filter.setOnClickListener { showFilterPopupMenu(binding.filter) }
        setHasOptionsMenu(true)
        observeRestaurant()
        search()

    }

    private fun showSortPopupMenu(view: View) {
        val popup = PopupMenu(this.requireContext(), view)
        popup.inflate(R.menu.sort_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {

                R.id.sort_by_rate -> {
                    sort = getString(R.string.rating)
                }
                R.id.sort_by_distance -> {
                    sort = getString(R.string.distance)

                }
                R.id.reset -> {
                    sort = ""
                }

            }

            restaurantsViewModel.sortRestaurants(sort)
            true
        }

        popup.show()
    }

    private fun showFilterPopupMenu(view: View) {
        val popup = PopupMenu(this.requireContext(), view)
        popup.inflate(R.menu.filter_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {

                R.id.filter_offer -> {
                    filter = getString(R.string.has_offer)
                }
                R.id.all -> {
                    filter = ""
                }

            }

            restaurantsViewModel.filterRestaurants(filter)
            true
        }

        popup.show()
    }

    fun observeRestaurant(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                restaurantsViewModel.restaurantUi.collect {
                    when {
                        it.isLoading -> binding.loading.visibility = View.VISIBLE
                        it.restaurantsItemList != null -> {
                            binding.loading.visibility = View.GONE
                            bindRecyclerView(binding.recyclerView, it.restaurantsItemList)
                        }
                        it.message.isNotEmpty() -> {
                            binding.loading.visibility = View.GONE
                            binding.errorMessage.visibility = View.VISIBLE
                            binding.errorMessage.text = it.message

                        }
                    }
                }
            }
        }
    }

    fun search(){
        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding!!.searchView.clearFocus()
                restaurantsViewModel.search(query!!)

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                restaurantsViewModel.search(query!!)

                return true
            }

        })
    }

}