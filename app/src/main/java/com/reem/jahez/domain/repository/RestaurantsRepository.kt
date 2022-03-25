package com.reem.jahez.domain.repository

import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.models.Restaurants

interface RestaurantsRepository {
    suspend fun getRestaurants(): List<RestaurantItem>
}