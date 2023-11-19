package com.srhan.foodapp.ui.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.srhan.foodapp.R
import com.srhan.foodapp.databinding.FragmentMealsBottomsSheetBinding
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.ui.activites.DetailsMealActivity
import com.srhan.foodapp.util.Constants.Companion.MEAL_ID
import com.srhan.foodapp.util.Constants.Companion.MEAL_STR
import com.srhan.foodapp.util.Constants.Companion.MEAL_THUMB
import com.srhan.foodapp.util.Resource
import com.srhan.foodapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"


@AndroidEntryPoint
class MealsBottomsSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    private lateinit var binding: FragmentMealsBottomsSheetBinding

    private val viewModel: DetailViewModel by viewModels()

    lateinit var meal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealsBottomsSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        param1?.let { viewModel.getMealById(it) }
        observeOnMealInfo()
        onBottomSheetClick()


    }

    private fun onBottomSheetClick() {
        binding.bottomSheet.setOnClickListener {
            val intent = Intent(activity, DetailsMealActivity::class.java)
            intent.apply {
                putExtra(MEAL_ID, meal.idMeal)
                putExtra(MEAL_THUMB, meal.strMealThumb)
                putExtra(MEAL_STR, meal.strMeal)

            }
            startActivity(intent)
        }
    }

    private fun observeOnMealInfo() {
        lifecycleScope.launch {
            viewModel.mealDetailById.collect { response ->

                when (response) {
                    is Resource.Success -> {

                        response.data?.let { mealResult ->
                            meal = mealResult
                            binding.apply {
                                tvMealCategory.text = meal.strCategory
                                tvMealArea.text = meal.strArea
                                tvBottomSheetCategoryName.text = meal.strCategory
                                Glide.with(this@MealsBottomsSheetFragment).load(meal.strMealThumb)
                                    .into(imgCategory)

                            }
                        }

                    }

                    is Resource.Error -> {

                        response.message?.let { e ->
                            Log.d("BottomFragmentError", "getMealDetails: $e")
                        }


                    }


                    else -> {

                    }
                }

            }

        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MealsBottomsSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}