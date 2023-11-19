package com.srhan.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srhan.foodapp.databinding.CustomMealItemBinding
import com.srhan.foodapp.models.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()
    var onItemClick: ((MealsByCategory) -> Unit)? = null

    fun setMealsList(mealList: ArrayList<MealsByCategory>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            CustomMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val categoryMeal = mealList[position]
        holder.bind(categoryMeal)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    /////////////////////////////////////////////////////////////////
    inner class CategoryMealsViewHolder(private val itemBinding: CustomMealItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(mealsByCategory: MealsByCategory) {
            itemBinding.apply {
                Glide.with(root.context).load(mealsByCategory.strMealThumb)
                    .into(ivMeal)
                tvMealName.text = mealsByCategory.strMeal

                root.setOnClickListener {
                    onItemClick?.invoke(mealsByCategory)
                }
            }

        }


    }

}