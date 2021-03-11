package com.example.trasparenciagov.preference


import android.content.SharedPreferences

class AppPreferences(private val preferences: SharedPreferences) {

    fun saveListStringKey(key:String ,value: String) {
        val edit = preferences.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun getStringKey(key: String) = preferences.getString(key,null)




    companion object {
        const val NAME = "TrasparenciaGov"
        const val UF = "UF"

    }
}