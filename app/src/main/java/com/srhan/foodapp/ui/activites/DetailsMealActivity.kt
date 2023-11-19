package com.srhan.foodapp.ui.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.srhan.foodapp.R
import com.srhan.foodapp.databinding.ActivityDetailsMealBinding
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.util.Constants.Companion.MEAL_ID
import com.srhan.foodapp.util.Constants.Companion.MEAL_STR
import com.srhan.foodapp.util.Constants.Companion.MEAL_THUMB
import com.srhan.foodapp.util.Resource
import com.srhan.foodapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMealBinding
    private val detailViewModel: DetailViewModel by viewModels()
    lateinit var mealId: String
    private lateinit var mealStr: String
    private lateinit var youtubeUrl: String
    lateinit var mealThumb: String
    lateinit var meal: Meal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInfoFromIntent()
        setUpViewWithMealInformation()
        detailViewModel.getMealDetail(mealId)
        getMealDetails()
        onYoutubeIconClicked()
        onFloatingButtonClicked()

    }

    private fun onFloatingButtonClicked() {
        binding.floatingFavMeal.setOnClickListener {
            detailViewModel.upsertMeal(meal)
            Snackbar.make(findViewById(android.R.id.content), "Meal Saved", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun onYoutubeIconClicked() {
        binding.ivYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            startActivity(intent)
        }

    }

    private fun getMealInfoFromIntent() {
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
        mealStr = intent.getStringExtra(MEAL_STR)!!
    }


    private fun getMealDetails() {
        lifecycleScope.launch {
            detailViewModel.mealDetail.collect { response ->
                showLoading()
                when (response) {
                    is Resource.Success -> {
                        stopLoading()
                        response.data?.let { mealResult ->
                            binding.apply {
                                meal = mealResult
                                stopLoading()
                                tvCategory.text = "Category : ${meal.strCategory}"
                                tvAreaInfo.text = "Area : ${meal.strArea}"
                                tvInfo.text = meal.strInstructions
                                youtubeUrl = meal.strYoutube!!

                            }
                        }

                    }

                    is Resource.Error -> {
                        stopLoading()
                        response.message?.let { e ->
                            Log.d("DetailsMealActivityError", "getMealDetails: $e")
                        }


                    }

                    is Resource.Loading -> {
                        showLoading()

                    }

                    else -> {

                    }
                }

            }

        }
    }


    private fun setUpViewWithMealInformation() {
        binding.apply {
            collapsingToolbar.title = mealStr
            collapsingToolbar.setCollapsedTitleTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            collapsingToolbar.setExpandedTitleColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            Glide.with(applicationContext)
                .load(mealThumb)
                .into(ivDetailMeal)
        }
    }

    private fun showLoading() {
        binding.apply {
            progressbar.visibility = View.VISIBLE
            floatingFavMeal.visibility = View.GONE
            tvCategory.visibility = View.INVISIBLE
            tvAreaInfo.visibility = View.INVISIBLE
            tvInstructions.visibility = View.INVISIBLE
            tvInfo.visibility = View.INVISIBLE
            ivYoutube.visibility = View.INVISIBLE
        }

    }


    private fun stopLoading() {
        binding.apply {
            progressbar.visibility = View.GONE
            floatingFavMeal.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvAreaInfo.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
            tvInfo.visibility = View.VISIBLE
            ivYoutube.visibility = View.VISIBLE
        }

    }

}