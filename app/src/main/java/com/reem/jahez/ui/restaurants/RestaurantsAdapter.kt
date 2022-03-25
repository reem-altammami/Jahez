package com.reem.jahez.ui.restaurants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reem.jahez.databinding.RestaurantItemBinding
import com.reem.jahez.domain.models.RestaurantsItemUi


class RestaurantsAdapter (): ListAdapter<RestaurantsItemUi,RestaurantsAdapter.RestaurantsViewHolder>(DiffCallback) {
    class RestaurantsViewHolder(private val binding :RestaurantItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantItem: RestaurantsItemUi){
            binding.restaurant = restaurantItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
return RestaurantsViewHolder((RestaurantItemBinding.inflate(LayoutInflater.from(parent.context))))
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        val restaurantItem=getItem(position)
        holder.bind(restaurantItem)
    }
    companion object DiffCallback:DiffUtil.ItemCallback<RestaurantsItemUi>(){
        override fun areItemsTheSame(oldItem: RestaurantsItemUi, newItem: RestaurantsItemUi): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: RestaurantsItemUi, newItem: RestaurantsItemUi): Boolean {
            TODO("Not yet implemented")
        }

    }
}