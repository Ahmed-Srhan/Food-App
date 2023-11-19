package com.srhan.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srhan.foodapp.databinding.CustomMealItemBinding
import com.srhan.foodapp.models.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoriteMealsViewHolder>() {


    /////////////////////////////////////////////////////////////////
    private class ItemDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, ItemDiffCallback())

    /////////////////////////////////////////////////////////////////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealsViewHolder {
        return FavoriteMealsViewHolder(
            CustomMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /////////////////////////////////////////////////////////////////
    inner class FavoriteMealsViewHolder(private val itemBinding: CustomMealItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(meal: Meal) {
            itemBinding.apply {
                Glide.with(root.context).load(meal.strMealThumb)
                    .into(ivMeal)
                tvMealName.text = meal.strMeal


            }

        }

    }
}