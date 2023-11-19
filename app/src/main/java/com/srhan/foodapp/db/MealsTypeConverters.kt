package com.srhan.foodapp.db

import androidx.room.TypeConverter

class MealsTypeConverters {

    @TypeConverter
    fun fromStringToAny(s: String?): Any {
        if (s == null) {
            return ""
        }
        return s
    }

    @TypeConverter
    fun fromAnyTOString(any: Any?): String {
        if (any == null) {
            return ""
        }
        return any.toString()
    }
}