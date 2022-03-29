package com.reem.jahez.ui.restaurants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.models.RestaurantsItemUi
import com.reem.jahez.domain.models.RestaurantsUi
import com.reem.jahez.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(val gerRestaurantsUseCase: GetRestaurantsUseCase) :
    ViewModel() {
    private val _restaurantUi: MutableStateFlow<RestaurantsUi> = MutableStateFlow(RestaurantsUi())
    val restaurantUi: StateFlow<RestaurantsUi> = _restaurantUi


    init {
        getRestaurants()
    }

    fun getRestaurants() {
        viewModelScope.launch {
            val restaurants = gerRestaurantsUseCase().collect { response ->
                when (response) {
                    is Resource.Loading -> _restaurantUi.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        val restaurantList = response.data!!.map {
                            RestaurantsItemUi(
                                name = it.name!!,
                                image = it.image!!,
                                hasOffer = it.hasOffer!!,
                                offer = it.offer ?: "",
                                distance = it.distance!!,
//                                distance = String.format("%.1f", it.distance),
                                rate = it.rating!!.toString(),
                            )
                        }
                        Log.e("list", "${restaurantList}")
                        _restaurantUi.update {
                            it.copy(
                                restaurantsItemList = restaurantList,
                                isLoading = false,
                                message = ""
                            )
                        }
                    }
                    is Resource.Error -> _restaurantUi.update {
                        it.copy(
                            message = response.message.toString(),
                            isLoading = false,
                            restaurantsItemList = listOf()
                        )
                    }
                }

            }
        }
    }

    fun sortRestaurants(
        sort: String
    ) {
        val list = restaurantUi.value.restaurantsItemList
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
            it.copy(
                restaurantsItemList = newList
            )
        }
    }

    fun filterRestaurants(filter: String) {
        val list = _restaurantUi.value.restaurantsItemList
        var newList = listOf<RestaurantsItemUi>()
        if (filter.isNotEmpty()) {
            newList = list.filter { it.hasOffer }
        } else {
            getRestaurants()
        }
        _restaurantUi.update {
            it.copy(
                restaurantsItemList = newList
            )
        }

    }

    fun search(name: String) {
        val list = _restaurantUi.value.restaurantsItemList
        var newList = listOf<RestaurantsItemUi>()
        if (name.isNotEmpty()) {
            newList = list?.filter { it.name!!.toLowerCase().contains(name.toLowerCase()) }
        } else {
            getRestaurants()
        }
        _restaurantUi.update {
            it.copy(
                restaurantsItemList = newList
            )
        }
    }
}