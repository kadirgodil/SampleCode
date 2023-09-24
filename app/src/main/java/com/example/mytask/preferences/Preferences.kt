package com.example.mytask.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class Preferences(context: Context?) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getInt(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun putInt(key: String?, value: Int?) {
        editor.putInt(key, value!!)
        editor.commit()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }



    fun remove(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }


    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPreferences.edit()
    }


}