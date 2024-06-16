package com.bangkidss.scholarseeks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkidss.scholarseeks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserPreference = UserPreference(this)
        userModel = mUserPreference.getUser()

        checkPreference()
        setContentView(R.layout.activity_main)
    }

    private fun checkPreference() {
        if (userModel.user_id.isNullOrEmpty() || userModel.jwt_token.isNullOrEmpty()) {
            val intentAuth = Intent(this, GetStartedActivity::class.java)
            startActivity(intentAuth)
            finish()
        } else {
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }
    }
}