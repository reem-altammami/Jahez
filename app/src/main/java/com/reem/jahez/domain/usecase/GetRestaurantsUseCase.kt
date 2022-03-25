package com.reem.jahez.domain.usecase

import android.util.Log
import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.models.Restaurants
import com.reem.jahez.domain.repository.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(private val restaurantsRepo: RestaurantsRepository) {
     suspend operator fun invoke() : Flow<Response<List<RestaurantItem>>> = flow{
          try {
               emit(Response.Loading())
               val restaurants = restaurantsRepo.getRestaurants()
               emit(Response.Success(data = restaurants))
               Log.e("res","$restaurants")

          }catch (e:Exception){
               emit(Response.Error(message = e.message.toString()))
               Log.e("res","${e.message.toString()}")
          }
     }
}