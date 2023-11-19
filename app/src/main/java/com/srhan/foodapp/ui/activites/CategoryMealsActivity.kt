package com.srhan.foodapp.ui.activites

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.srhan.foodapp.adapters.CategoryMealsAdapter
import com.srhan.foodapp.databinding.ActivityCategoryMealsBinding
import com.srhan.foodapp.models.MealsByCategory
import com.srhan.foodapp.util.Constants.Companion.CATEGORY_NAME
import com.srhan.foodapp.util.Resource
import com.srhan.foodapp.viewmodel.MealsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private val mealsByCategoryViewModel: MealsByCategoryViewModel by viewModels()
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryName = intent.getStringExtra(CATEGORY_NAME)!!

        setUpCategoriesRecyclerView()
        mealsByCategoryViewModel.getPopularMeals(categoryName)
        handleCategoryMealsChange()
    }


    private fun handleCategoryMealsChange() {

        lifecycleScope.launch {
            mealsByCategoryViewModel.meals.collect { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { categoryList ->
                            categoryMealsAdapter.setMealsList(categoryList as ArrayList<MealsByCategory>)
                            binding.tvCategoryCount.text = categoryList.size.toString()
                        }

                    }

                    is Resource.Error -> {
                        response.message?.let { e ->
                            Log.d("CategoryMealsActivityError : ", e)
                        }


                    }

                    else -> {

                    }
                }

            }

        }

    }

    private fun setUpCategoriesRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            adapter = categoryMealsAdapter
            layoutManager =
                GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false)
        }
    }


}