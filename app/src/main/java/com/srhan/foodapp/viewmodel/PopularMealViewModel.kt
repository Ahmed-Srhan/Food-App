package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.MealsByCategory
import com.srhan.foodapp.repository.GetPopularMealRepo
import com.srhan.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMealViewModel @Inject constructor(private val getPopularMealRepo: GetPopularMealRepo) :
    ViewModel() {

    private val _popularMeals: MutableStateFlow<Resource<List<MealsByCategory>>?> =
        MutableStateFlow(null)
    val popularMeals: StateFlow<Resource<List<MealsByCategory>>?> = _popularMeals

    private val categoryName: String = "Seafood"

    init {
        getPopularMeals(categoryName)
    }

    private fun getPopularMeals(categoryName: String) = viewModelScope.launch {
        getPopularMealRepo.getPopularMeal(categoryName).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.meals
                    _popularMeals.value = Resource.Success(meal!!)
                }

                is Resource.Error -> {
                    _popularMeals.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _popularMeals.value = Resource.Loading()
                }

            }
        }

    }


}