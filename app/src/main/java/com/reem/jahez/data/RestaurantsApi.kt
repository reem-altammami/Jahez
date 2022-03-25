package com.reem.jahez.data

import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.models.Restaurants
import retrofit2.http.GET

interface RestaurantsApi {
    @GET("restaurants.json")
    suspend fun getRestaurants() : List<RestaurantItem>

}