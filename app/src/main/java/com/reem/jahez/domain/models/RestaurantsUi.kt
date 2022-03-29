package com.reem.jahez.domain.models

data class RestaurantsUi (
    val restaurantsItemList :List<RestaurantsItemUi> = listOf(),
    val isLoading : Boolean = false,
    val message : String= ""
        )
data class RestaurantsItemUi(
    val name : String= "",
    val image: String="",
    val hasOffer: Boolean=false,
    val offer :String? =null,
    val distance:  Double =0.0,
    val rate :  String= ""
) {
    fun convertToString(dis: Double): String {
        return String.format("%.2f", dis)
    }
}