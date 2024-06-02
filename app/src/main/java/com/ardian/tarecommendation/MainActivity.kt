package com.ardian.tarecommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardian.tarecommendation.databinding.ActivityGetStartedBinding
import com.ardian.tarecommendation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isLogin = false
        if (!isLogin) {
            val intentAuth = Intent(this, GetStartedActivity::class.java)
            startActivity(intentAuth)
        }
        setContentView(R.layout.activity_main)
    }
}