package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.repository.MealsLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(private val localRepository: MealsLocalRepository) :
    ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> get() = _meals


    fun getAllFavoriteMeals() {
        viewModelScope.launch {
            localRepository.getAllFavouriteMeals().collect { mealsList ->
                _meals.value = mealsList
            }
        }
    }

    fun upsertMeal(meal: Meal) = viewModelScope.launch {
        localRepository.upsert(meal)
    }

    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        localRepository.delete(meal)
    }


}



