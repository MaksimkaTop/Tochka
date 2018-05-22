package com.maks.hp.tochka

import android.app.Activity
import android.preference.PreferenceManager

class PrefUtils

(private val activity: Activity) {

    val token: String?
        get() {
            val sp = PreferenceManager.getDefaultSharedPreferences(activity)
            return sp.getString("token", null)
        }

    fun saveAccessToken(token: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = sp.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun clearToken() {
        val sp = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}