package com.rosen.ezy.utils

import android.content.Context
import android.content.SharedPreferences

class EzyPrefs {

    companion object {

        const val ezy = "EzyPrefs"

        fun setPreferences(context: Context, value : String, key : String) {
            val editor : SharedPreferences.Editor = context.getSharedPreferences(ezy, Context.MODE_PRIVATE).edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getPreferences(context: Context, key : String) : String? {
            val sharedPreferences = context.getSharedPreferences(ezy, Context.MODE_PRIVATE)
            return sharedPreferences.getString(key, "")
        }

    }
}