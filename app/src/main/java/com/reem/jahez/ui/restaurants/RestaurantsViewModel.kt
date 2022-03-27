package com.reem.jahez.ui.restaurants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.models.RestaurantsItemUi
import com.reem.jahez.domain.models.RestaurantsUi
import com.reem.jahez.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(val gerRestaurantsUseCase: GetRestaurantsUseCase): ViewModel(){
    private val _restaurantUi : MutableStateFlow<RestaurantsUi> = MutableStateFlow(RestaurantsUi())
    val restaurantUi : StateFlow<RestaurantsUi> = _restaurantUi
//    private var restaurantList : List<RestaurantItem> = listOf()
    var newList :List<RestaurantItem> = listOf()

    init{
        getRestaurants()
    }

     fun getRestaurants(sort:String="", filter: String="" ,search:String=""){
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
                      val    restaurantList = response.data!!
                           newList = if (search.isNotEmpty()){
                               search(search,restaurantList)
                           } else{
                               customList(sort,filter,restaurantList)
                           }
                          val customList =newList
                           .map {
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
                               restaurantsItemList = customList!!,
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

    private fun customList(sort:String, filter: String, list: List<RestaurantItem>?): List<RestaurantItem> {
        var newList = list!!
          if (filter.isNotEmpty()&& sort.isNotEmpty()) {
                  if (sort=="distance") {
                      newList= list!!.sortedBy { it.distance }.filter { it.hasOffer!! }
                  } else
                      if(sort =="rating"){
                          newList=  list!!.sortedBy { it.rating }.filter { it.hasOffer!! }
                      }
        } else
            if (sort.isNotEmpty() && filter.isEmpty()){
                   if (sort=="distance") {
                       newList= list!!.sortedBy { it.distance }
                } else
                    if(sort =="rating"){
                        newList=  list!!.sortedBy { it.rating }
                }
            }else if(sort.isEmpty()&&filter.isNotEmpty()){
                newList=   list!!.filter { it.hasOffer!! }
            } else{
                newList= list!!
            }
        return  newList
    }

    private fun search(name: String, list: List<RestaurantItem>?): List<RestaurantItem> {
        var newList : MutableList<RestaurantItem> = mutableListOf()
for (item in 0 until  list!!.size)
    if (list[item].name?.toLowerCase()!!.contains(name)) {
        val restaurant = list[item]
        newList.add(restaurant!!)
    }
        return newList
    }
}