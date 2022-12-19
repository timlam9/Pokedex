package com.lamti.pokemon.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromStatList(countryLang: List<StatDB?>?): String? {
        if (countryLang == null) return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<StatDB?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toStatList(countryLangString: String?): List<StatDB>? {
        if (countryLangString == null) return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<StatDB?>?>() {}.type
        return gson.fromJson(countryLangString, type)
    }
}