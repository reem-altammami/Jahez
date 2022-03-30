package com.reem.jahez.ui.restaurants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.base.BaseViewModel
import com.reem.jahez.domain.models.*
import com.reem.jahez.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(val gerRestaurantsUseCase: GetRestaurantsUseCase) :
    BaseViewModel() {

    private val _restaurantUi: MutableStateFlow<List<RestaurantsItemUi>> =
        MutableStateFlow(listOf())
    val restaurantUi: StateFlow<List<RestaurantsItemUi>> = _restaurantUi

    init {
        getRestaurants()
    }

    fun getRestaurants() {
        viewModelScope.launch {
            val restaurants = gerRestaurantsUseCase().collect { response ->
                when (response) {
                    is Resource.Loading ->
                        _uiState.emit(UiState(isLoading = true))
                    is Resource.Success -> {
                        val restaurant = response.data?.map {
                            RestaurantsItemUi(
                                name = it.name!!,
                                image = it.image!!,
                                hasOffer = it.hasOffer!!,
                                offer = it.offer ?: "",
                                distance = it.distance!!,
                                rate = it.rating!!.toString(),
                            )
                        }
                        _restaurantUi.update { restaurant!! }
                        _uiState.emit(UiState())
                    }
                    is Resource.Error ->

                        _uiState.emit(UiState(message = response.message.toString()))

                }

            }
        }
    }

    fun sortRestaurants(
        sort: String
    ) {
        val list = restaurantUi.value

        var newList = listOf<RestaurantsItemUi>()
        if (sort == "distance") {
            newList = list!!.sortedBy { it.distance }
        } else
            if (sort == "rating") {
                newList = list.sortedBy { it.rate }
            } else if (sort.isEmpty()) {
                getRestaurants()
            }
        _restaurantUi.update {
            newList
        }
    }

    fun filterRestaurants(filter: String) {
        val list = _restaurantUi.value

        var newList = listOf<RestaurantsItemUi>()
        if (filter.isNotEmpty()) {
            newList = list.filter { it.hasOffer }
        } else {
            getRestaurants()
        }
        _restaurantUi.update {
            newList
        }

    }

    fun search(name: String) {
        val list = _restaurantUi.value

        var newList = listOf<RestaurantsItemUi>()
        if (name.isNotEmpty()) {
            newList = list?.filter { it.name!!.toLowerCase().contains(name.toLowerCase()) }
        } else {
            getRestaurants()
        }
        _restaurantUi.update {
            newList
        }
    }
}