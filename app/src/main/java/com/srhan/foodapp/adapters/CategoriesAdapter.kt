package com.srhan.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srhan.foodapp.databinding.CustomCategoryItemBinding
import com.srhan.foodapp.models.Category

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoryList = ArrayList<Category>()
    var onCategoryClick: ((Category) -> Unit)? = null


    fun setCategoryList(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    /////////////////////////////////////////////////////////////////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CustomCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    /////////////////////////////////////////////////////////////////
    inner class CategoriesViewHolder(private val itemBinding: CustomCategoryItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category) {
            itemBinding.apply {
                Glide.with(root.context).load(category.strCategoryThumb).into(ivCategory)

                root.setOnClickListener {
                    onCategoryClick!!.invoke(category)
                }
                tvCategoryName.text = category.strCategory
            }

        }


    }
}