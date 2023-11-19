package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.MealsByCategory
import com.srhan.foodapp.repository.GetMealByCategoryRepo
import com.srhan.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsByCategoryViewModel @Inject constructor(private val getMealByCategoryRepo: GetMealByCategoryRepo) :
    ViewModel() {


    private val _meals: MutableStateFlow<Resource<List<MealsByCategory>>?> = MutableStateFlow(null)
    val meals: StateFlow<Resource<List<MealsByCategory>>?> = _meals


    fun getPopularMeals(categoryName: String) = viewModelScope.launch {
        getMealByCategoryRepo.getMealByCategory(categoryName).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.meals
                    _meals.value = Resource.Success(meal!!)
                }

                is Resource.Error -> {
                    _meals.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _meals.value = Resource.Loading()
                }

            }
        }

    }


}