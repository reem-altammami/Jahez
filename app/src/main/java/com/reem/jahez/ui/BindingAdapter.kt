package com.reem.jahez.ui

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reem.jahez.R
import com.reem.jahez.domain.models.RestaurantsItemUi
import com.reem.jahez.ui.restaurants.RestaurantsAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data : List<RestaurantsItemUi>) {
    val adapter = recyclerView.adapter as RestaurantsAdapter
    adapter.submitList(data)
}

@BindingAdapter("poster")
fun bindImage(imageView: ImageView,imgUrl:String){
   imgUrl.let {
       val imgUri = imgUrl.toUri().buildUpon().build()
       Glide.with(imageView)
           .load("$imgUri")
           .placeholder(R.drawable.loading_animation)
           .into(imageView)

   }
}