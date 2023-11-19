package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.Category
import com.srhan.foodapp.repository.GetCategoriesRepo
import com.srhan.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryMealsViewModel @Inject constructor(private val getCategoriesRepo: GetCategoriesRepo) :
    ViewModel() {

    private val _categoryMeals: MutableStateFlow<Resource<List<Category>>?> = MutableStateFlow(null)
    val categoryMeals: StateFlow<Resource<List<Category>>?> = _categoryMeals

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        getCategoriesRepo.getCategories().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.categories
                    _categoryMeals.value = Resource.Success(meal!!)
                }

                is Resource.Error -> {
                    _categoryMeals.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _categoryMeals.value = Resource.Loading()
                }

            }
        }
    }

}