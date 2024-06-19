package com.bangkidss.scholarseeks

import android.content.Context

internal class UserPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val JWT_TOKEN = "jwt_token"
        private const val USER_ID = "user_id"
        private const val ID_TOKEN = "id_token"
        private const val USER_NAME = "user_name"
        private const val USER_EMAIL = "user_email"
        private const val USER_PHOTO = "user_photo"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel) {
        val editor = preference.edit()
        editor.putString(USER_ID, value.user_id)
        editor.putString(JWT_TOKEN, value.jwt_token)
        editor.putString(ID_TOKEN, value.id_token)
        editor.putString(USER_NAME, value.user_name)
        editor.putString(USER_EMAIL, value.user_email)
        editor.putString(USER_PHOTO, value.user_photo)
        editor.apply()
    }

    fun getUser() : UserModel {
        val model = UserModel()
        model.user_id = preference.getString(USER_ID, "")
        model.jwt_token = preference.getString(JWT_TOKEN, "")
        model.id_token = preference.getString(ID_TOKEN, "")
        model.user_name = preference.getString(USER_NAME, "")
        model.user_email = preference.getString(USER_EMAIL, "")
        model.user_photo = preference.getString(USER_PHOTO, "")

        return model
    }

    fun clearUser() {
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }

}