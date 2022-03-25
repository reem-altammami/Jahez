package com.reem.jahez.data.repository

import com.reem.jahez.data.RestaurantsApi
import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.models.Restaurants
import com.reem.jahez.domain.repository.RestaurantsRepository
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val restaurantsApi : RestaurantsApi) : RestaurantsRepository {
    override suspend fun getRestaurants(): List<RestaurantItem> {
       return restaurantsApi.getRestaurants()
    }
}