package com.reem.jahez.data.repository


import com.reem.jahez.data.RestaurantsApi
import com.reem.jahez.domain.models.Resource

import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.repository.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val restaurantsApi : RestaurantsApi) : RestaurantsRepository {
    override suspend fun getRestaurants(): Flow<Resource<List<RestaurantItem>>> =  flow{

        emit(Resource.Loading())
        val restaurants = restaurantsApi.getRestaurants()
        if (restaurants.isSuccessful) {
           emit(Resource.Success(data = restaurants.body()!!))
        }else {
            emit(Resource.Error(message = restaurants.message().toString()))
        }

    }
}