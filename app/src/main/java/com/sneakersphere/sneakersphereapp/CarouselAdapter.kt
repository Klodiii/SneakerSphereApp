package com.sneakersphere.sneakersphereapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

class CarouselAdapter(private val itemList: List<Any>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carousel, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = itemList[position]

        when (item) {
            is Int -> {
                // It's a resource ID for the carousel
                holder.imageView.setImageResource(item)
            }
            is Product -> {
                // It's a Product object for the product list
                holder.imageView.setImageResource(item.imageResource)
            }
            else -> throw IllegalArgumentException("Unknown item type at position: $position")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.imageView)
    }
}