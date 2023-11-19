package com.srhan.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.srhan.foodapp.R
import com.srhan.foodapp.adapters.CategoriesAdapter
import com.srhan.foodapp.adapters.PopularMealsAdapter
import com.srhan.foodapp.databinding.FragmentHomeBinding
import com.srhan.foodapp.models.Category
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.models.MealsByCategory
import com.srhan.foodapp.ui.activites.CategoryMealsActivity
import com.srhan.foodapp.ui.activites.DetailsMealActivity
import com.srhan.foodapp.ui.fragments.bottomsheet.MealsBottomsSheetFragment
import com.srhan.foodapp.util.Constants.Companion.CATEGORY_NAME
import com.srhan.foodapp.util.Constants.Companion.MEAL_ID
import com.srhan.foodapp.util.Constants.Companion.MEAL_STR
import com.srhan.foodapp.util.Constants.Companion.MEAL_THUMB
import com.srhan.foodapp.util.Resource
import com.srhan.foodapp.viewmodel.CategoryMealsViewModel
import com.srhan.foodapp.viewmodel.PopularMealViewModel
import com.srhan.foodapp.viewmodel.RandomMealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularMealsAdapter: PopularMealsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val randomMealViewModel: RandomMealViewModel by viewModels()
    private val popularMealViewModel: PopularMealViewModel by viewModels()
    private val categoryMealViewModel: CategoryMealsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        randomMealViewModel.getRandomMeals()
        handleRandomMealResult()
        onRandomMealClick()


        setUpPopularRecyclerView()
        handlePopularMealResult()
        onPopularItemClick()
        onPopularItemLongClick()

        setUpCategoriesRecyclerView()
        handleCategoryResult()
        onCategoryItemClick()
        onCategoryItemClick()

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_searchMealFragment
            )

        }


    }

    private fun onPopularItemLongClick() {
        popularMealsAdapter.onItemLongClick = { meal ->
            val mealSheetDialogFragment = MealsBottomsSheetFragment.newInstance(meal.idMeal)
            mealSheetDialogFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryItemClick() {
        categoriesAdapter.onCategoryClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)

        }
    }

    private fun onPopularItemClick() {
        popularMealsAdapter.onItemClick = { categoryMeal ->
            val intent = Intent(activity, DetailsMealActivity::class.java)
            intent.putExtra(MEAL_ID, categoryMeal.idMeal)
            intent.putExtra(MEAL_THUMB, categoryMeal.strMealThumb)
            intent.putExtra(MEAL_STR, categoryMeal.strMeal)
            startActivity(intent)

        }

    }

    private fun handleCategoryResult() {
        lifecycleScope.launch {
            categoryMealViewModel.categoryMeals.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { mealResult ->
                            categoriesAdapter.setCategoryList(mealResult as ArrayList<Category>)
                        }
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "getRandomMeal: ${result.message.toString()}")
                    }


                    else -> {}
                }

            }
        }

    }

    private fun handlePopularMealResult() {
        lifecycleScope.launch {
            popularMealViewModel.popularMeals.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { mealResult ->
                            popularMealsAdapter.setMealsList(mealResult as ArrayList<MealsByCategory>)
                        }
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "getRandomMeal: ${result.message.toString()}")
                    }


                    else -> {}
                }

            }
        }
    }

    //try using fragment instead of activity
    private fun onRandomMealClick() {
        binding.ivRandomMeal.setOnClickListener {
            val intent = Intent(activity, DetailsMealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            intent.putExtra(MEAL_STR, randomMeal.strMeal)
            startActivity(intent)
        }
    }

    private fun handleRandomMealResult() {
        lifecycleScope.launch {
            randomMealViewModel.randomMeals.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        //hideProgressBar()
                        result.data?.let { mealResult ->
                            randomMeal = mealResult
                            Glide.with(this@HomeFragment)
                                .load(mealResult.strMealThumb)
                                .into(binding.ivRandomMeal)
                        }
                    }

                    is Resource.Error -> {
                        //hideProgressBar()
                        Log.d(TAG, "getRandomMeal: ${result.message.toString()}")
                    }

                    is Resource.Loading -> {
                        //showProgressBar()
                    }

                    null -> TODO()
                }


            }


        }
    }

    private fun setUpPopularRecyclerView() {
        popularMealsAdapter = PopularMealsAdapter()
        binding.rvPopularItem.apply {
            adapter = popularMealsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategory.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        }
    }


    companion object {
        private const val TAG = "HomeFragmentRandomMealError"

    }

}
