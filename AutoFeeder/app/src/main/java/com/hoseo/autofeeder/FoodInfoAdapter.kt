package com.hoseo.autofeeder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_row.view.*
import kotlinx.android.synthetic.main.log_row.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FoodInfoAdapter(private val foods: MutableList<FoodInfo>) : RecyclerView.Adapter<FoodInfoAdapter.FoodInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = FoodInfoViewHolder(parent)

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holer: FoodInfoViewHolder, position: Int) {
        foods[position].let { item ->
            with(holer) {
                val foodName = item.foodName
                val foodDesc = item.foodDesc
                val foodImageURL = item.foodImageURL

                textFoodName.text = "$foodName"
                textFoodDesc.text = "$foodDesc"
                Picasso.get().load(foodImageURL).into(foodImage)
            }
        }
    }

    inner class FoodInfoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.food_row, parent, false)) {
        val textFoodName = itemView.textFoodName
        val textFoodDesc = itemView.textFoodDesc
        val foodImage = itemView.foodImage
    }
}