package com.reem.jahez.domain.models

import com.squareup.moshi.Json

data class Restaurants(

	@Json(name="restaurant")
	val restaurants: List<RestaurantItem?>? = null
)

data class RestaurantItem(

	@Json(name="image")
	val image: String? = null,

	@Json(name="hours")
	val hours: String? = null,

	@Json(name="distance")
	val distance: Double? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="rating")
	val rating: Double? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="hasOffer")
	val hasOffer: Boolean? = null,

	@Json(name="offer")
	val offer: String? = null
)
