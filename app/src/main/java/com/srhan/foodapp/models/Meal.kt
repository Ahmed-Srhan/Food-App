package com.srhan.foodapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "meals_table")
data class Meal(
    val dateModified: Any?,
    @PrimaryKey
    val idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: Any?,
    val strDrinkAlternate: Any?,
    val strImageSource: Any?,
    val strIngredient1: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: Any?,
    val strIngredient17: Any?,
    val strIngredient18: Any?,
    val strIngredient19: Any?,
    val strIngredient2: String?,
    val strIngredient20: Any?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strMeasure1: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: Any?,
    val strMeasure17: Any?,
    val strMeasure18: Any?,
    val strMeasure19: Any?,
    val strMeasure2: String?,
    val strMeasure20: Any?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strSource: Any?,
    val strTags: String?,
    val strYoutube: String?
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meal

        if (dateModified != other.dateModified) return false
        if (idMeal != other.idMeal) return false
        if (strArea != other.strArea) return false
        if (strCategory != other.strCategory) return false
        if (strCreativeCommonsConfirmed != other.strCreativeCommonsConfirmed) return false
        if (strDrinkAlternate != other.strDrinkAlternate) return false
        if (strImageSource != other.strImageSource) return false
        if (strIngredient1 != other.strIngredient1) return false
        if (strIngredient10 != other.strIngredient10) return false
        if (strIngredient11 != other.strIngredient11) return false
        if (strIngredient12 != other.strIngredient12) return false
        if (strIngredient13 != other.strIngredient13) return false
        if (strIngredient14 != other.strIngredient14) return false
        if (strIngredient15 != other.strIngredient15) return false
        if (strIngredient16 != other.strIngredient16) return false
        if (strIngredient17 != other.strIngredient17) return false
        if (strIngredient18 != other.strIngredient18) return false
        if (strIngredient19 != other.strIngredient19) return false
        if (strIngredient2 != other.strIngredient2) return false
        if (strIngredient20 != other.strIngredient20) return false
        if (strIngredient3 != other.strIngredient3) return false
        if (strIngredient4 != other.strIngredient4) return false
        if (strIngredient5 != other.strIngredient5) return false
        if (strIngredient6 != other.strIngredient6) return false
        if (strIngredient7 != other.strIngredient7) return false
        if (strIngredient8 != other.strIngredient8) return false
        if (strIngredient9 != other.strIngredient9) return false
        if (strInstructions != other.strInstructions) return false
        if (strMeal != other.strMeal) return false
        if (strMealThumb != other.strMealThumb) return false
        if (strMeasure1 != other.strMeasure1) return false
        if (strMeasure10 != other.strMeasure10) return false
        if (strMeasure11 != other.strMeasure11) return false
        if (strMeasure12 != other.strMeasure12) return false
        if (strMeasure13 != other.strMeasure13) return false
        if (strMeasure14 != other.strMeasure14) return false
        if (strMeasure15 != other.strMeasure15) return false
        if (strMeasure16 != other.strMeasure16) return false
        if (strMeasure17 != other.strMeasure17) return false
        if (strMeasure18 != other.strMeasure18) return false
        if (strMeasure19 != other.strMeasure19) return false
        if (strMeasure2 != other.strMeasure2) return false
        if (strMeasure20 != other.strMeasure20) return false
        if (strMeasure3 != other.strMeasure3) return false
        if (strMeasure4 != other.strMeasure4) return false
        if (strMeasure5 != other.strMeasure5) return false
        if (strMeasure6 != other.strMeasure6) return false
        if (strMeasure7 != other.strMeasure7) return false
        if (strMeasure8 != other.strMeasure8) return false
        if (strMeasure9 != other.strMeasure9) return false
        if (strSource != other.strSource) return false
        if (strTags != other.strTags) return false
        if (strYoutube != other.strYoutube) return false

        return true
    }


}