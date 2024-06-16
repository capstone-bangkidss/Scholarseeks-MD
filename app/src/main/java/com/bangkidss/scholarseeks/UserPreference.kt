package com.bangkidss.scholarseeks

import android.content.Context

internal class UserPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val JWT_TOKEN = "jwt_token"
        private const val USER_ID = "user_id"
        private const val GOOGLE_TOKEN = "google_token"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel) {
        val editor = preference.edit()
        editor.putString(USER_ID, value.user_id)
        editor.putString(JWT_TOKEN, value.jwt_token)
        editor.putString(GOOGLE_TOKEN, value.google_token)
        editor.apply()
    }

    fun getUser() : UserModel {
        val model = UserModel()
        model.user_id = preference.getString(USER_ID, "")
        model.jwt_token = preference.getString(JWT_TOKEN, "")
        model.google_token = preference.getString(GOOGLE_TOKEN, "")

        return model
    }

    fun clearUser() {
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }

}