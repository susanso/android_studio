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

/*
    사료 정보를 표시할 Recycler View에 연결할 Adapter와 View Holder를 만드는 코드
 */

class FoodInfoAdapter(private val foods: MutableList<FoodInfo>) : RecyclerView.Adapter<FoodInfoAdapter.FoodInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = FoodInfoViewHolder(parent)

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holer: FoodInfoViewHolder, position: Int) {
        /*
            MutableList를 하나씩 불러와 해당 번호에 맞는 값을 TextView와 ImageView에 표시
         */
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

    /*
        food_row.xml의 레이아웃을 바탕으로 레이아웃을 생성할 ViewHolder
     */

    inner class FoodInfoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.food_row, parent, false)) {
        val textFoodName = itemView.textFoodName
        val textFoodDesc = itemView.textFoodDesc
        val foodImage = itemView.foodImage
    }
}