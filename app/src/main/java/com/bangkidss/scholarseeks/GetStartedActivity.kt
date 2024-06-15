package com.bangkidss.scholarseeks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkidss.scholarseeks.databinding.ActivityGetStartedBinding

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.getStarted.setOnClickListener {
            val intentSubjectArea = Intent(this, SubjectActivity::class.java)
            startActivity(intentSubjectArea)
        }


    }
}