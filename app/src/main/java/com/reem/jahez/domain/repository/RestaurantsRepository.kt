package com.reem.jahez.domain.repository

import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.models.RestaurantItem
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<Resource<List<RestaurantItem>>>
}