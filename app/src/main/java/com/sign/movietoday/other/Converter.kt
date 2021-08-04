package com.sign.movietoday.other

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sign.movietoday.models.movielistrequest.Result

class Converter {

    @TypeConverter
    fun fromResult(result : Result) : String{
        val string = Gson().toJson(result)
        return string
    }

    @TypeConverter
    fun fromString(string: String) : Result {
        val type = object : TypeToken<Result>(){}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromList(list : List<Int>) : String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(string: String) : List<Int>{
        val type = object : TypeToken<List<Int>>(){}.type
        return Gson().fromJson(string,type)
    }
}