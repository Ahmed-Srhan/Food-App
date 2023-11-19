package com.srhan.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srhan.foodapp.databinding.CustomPopularItemBinding
import com.srhan.foodapp.models.MealsByCategory

class PopularMealsAdapter : RecyclerView.Adapter<PopularMealsAdapter.PopularMealsViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()
    var onItemClick: ((MealsByCategory) -> Unit)? = null
    var onItemLongClick: ((MealsByCategory) -> Unit)? = null

    fun setMealsList(mealList: ArrayList<MealsByCategory>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealsViewHolder {
        return PopularMealsViewHolder(
            CustomPopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: PopularMealsViewHolder, position: Int) {
        val categoryMeal = mealList[position]
        holder.bind(categoryMeal)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    /////////////////////////////////////////////////////////////////
    inner class PopularMealsViewHolder(private val itemBinding: CustomPopularItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(mealsByCategory: MealsByCategory) {
            itemBinding.apply {
                Glide.with(root.context).load(mealsByCategory.strMealThumb)
                    .into(ivPopular)

                root.setOnClickListener {
                    onItemClick?.invoke(mealsByCategory)
                }
                root.setOnLongClickListener {
                    onItemLongClick?.invoke(mealsByCategory)
                    true
                }
            }

        }


    }

}