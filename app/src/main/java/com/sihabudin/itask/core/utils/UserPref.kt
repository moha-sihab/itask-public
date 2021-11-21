package com.sihabudin.itask.core.utils

import android.content.Context

internal class UserPref(context: Context) {
    companion object {
        private const val PREF_DEFAULT_DATA = "default_data_pref"
        private const val PREF_IS_DEFAULT ="is_default_pref"

    }

    private val preferences = context.getSharedPreferences(PREF_DEFAULT_DATA, Context.MODE_PRIVATE)

    fun setFlagDefaultData()
    {
        val editor = preferences.edit()
        editor.putBoolean(PREF_IS_DEFAULT,true)
        editor.apply()

    }

    fun getFlagDefaultData(): Boolean {
        return preferences.getBoolean(PREF_IS_DEFAULT, false)
    }

}