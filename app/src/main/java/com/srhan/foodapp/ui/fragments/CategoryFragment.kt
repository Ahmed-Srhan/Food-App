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
import androidx.recyclerview.widget.GridLayoutManager
import com.srhan.foodapp.adapters.CategoriesAdapter
import com.srhan.foodapp.databinding.FragmentCategoryBinding
import com.srhan.foodapp.models.Category
import com.srhan.foodapp.ui.activites.CategoryMealsActivity
import com.srhan.foodapp.util.Constants.Companion.CATEGORY_NAME
import com.srhan.foodapp.util.Resource
import com.srhan.foodapp.viewmodel.CategoryMealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private val viewModel: CategoryMealsViewModel by viewModels()
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCategoriesRecyclerView()
        handleCategoryMealsChange()
        categoriesAdapter.onCategoryClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun handleCategoryMealsChange() {
        lifecycleScope.launch {
            viewModel.categoryMeals.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { mealResult ->
                            categoriesAdapter.setCategoryList(mealResult as ArrayList<Category>)
                        }
                    }

                    is Resource.Error -> {
                        Log.d("CategoryFragmentError", result.message.toString())
                    }


                    else -> {}
                }
            }
        }
    }

    private fun setUpCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategory.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        }
    }


}