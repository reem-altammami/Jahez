package com.reem.jahez.data

import com.reem.jahez.domain.models.RestaurantItem
import retrofit2.http.GET

interface RestaurantsApi {
    @GET("restaurants.json")
    suspend fun getRestaurants() : retrofit2.Response<List<RestaurantItem>>

}