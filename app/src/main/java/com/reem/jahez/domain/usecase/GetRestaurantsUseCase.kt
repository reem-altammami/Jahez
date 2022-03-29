package com.reem.jahez.domain.usecase

import android.util.Log
import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.models.RestaurantItem
import com.reem.jahez.domain.repository.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(private val restaurantsRepo: RestaurantsRepository) {
     suspend operator fun invoke() : Flow<Resource<List<RestaurantItem>>> =
          restaurantsRepo.getRestaurants()
}