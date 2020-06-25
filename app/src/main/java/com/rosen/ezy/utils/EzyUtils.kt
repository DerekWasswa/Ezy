package com.rosen.ezy.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rosen.ezy.domain.data.Income
import java.text.SimpleDateFormat
import java.util.*

class EzyUtils {

    companion object {

        val gson = Gson()

        fun incomeToString(incomesList : List<Income>) : String {
            val type  = object : TypeToken<List<Income>>() {}.type
            return gson.toJson(incomesList, type)
        }

        fun stringToIncomeConversion(incomesList : String) : List<Income> {
            val type  = object : TypeToken<List<Income>>() {}.type
            return gson.fromJson(incomesList, type)
        }

        fun getDateFromTimestamp(timestamp : Long) : String {
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
        }

    }

}