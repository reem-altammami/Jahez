package com.reem.jahez.ui.restaurants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.RestaurantsItemUi
import com.reem.jahez.domain.models.RestaurantsUi
import com.reem.jahez.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(val gerRestaurantsUseCase: GetRestaurantsUseCase): ViewModel(){
    private val _restaurantUi : MutableStateFlow<RestaurantsUi> = MutableStateFlow(RestaurantsUi())
    val restaurantUi : StateFlow<RestaurantsUi> = _restaurantUi

    init{
        getRestaurants()
    }

    private fun getRestaurants(){
        viewModelScope.launch {
                val restaurants = gerRestaurantsUseCase().collect{ response ->
                   when(response){
                       is Response.Loading -> _restaurantUi.update {
                           it.copy(
                               isLoading = true
                           )
                       }
                       is Response.Success ->{
                           Log.e("res","${response.data}")
                          val restaurantList = response.data?.sortedByDescending { "rating"}?.map {
                              RestaurantsItemUi(
                                  name = it?.name!! ,
                                  image = it.image!!,
                                  hasOffer = it.hasOffer!!,
                                  offer = it.offer ,
                                  distance = String.format("%.1f", it.distance),
                                  rate = it.rating!!.toString(),
                              )

                          }
                           Log.e("list","${restaurantList}")
                           _restaurantUi.update {
                           it.copy(
                               restaurantsItemList = restaurantList!!,
                               isLoading = false,
                               message = ""
                           )
                       }
                   }
                       is Response.Error -> _restaurantUi.update {
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

    fun customList(sort:String, filter: String , list:List<RestaurantsItemUi>):List<RestaurantsItemUi>{
       return if (filter.isNotEmpty()) {
             list.sortedByDescending { sort }.filter { it.hasOffer }
        } else{
           list.sortedByDescending { sort }

       }

    }
}