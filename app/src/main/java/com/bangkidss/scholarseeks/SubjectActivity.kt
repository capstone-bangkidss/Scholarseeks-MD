package com.bangkidss.scholarseeks

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import com.bangkidss.scholarseeks.databinding.ActivitySubjectBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class SubjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectBinding

    private lateinit var autoCompleteTextView: MaterialAutoCompleteTextView
    private val suggestions = listOf("Science", "Technology", "Medic", "Education", "Civilization", "Law", "Artificial Intelligent", "Machine Learning", "Decision Making System", "Game", "Android", "Cloud", "Programming", "Analyst", "Agriculture", "Music", "Movie", "Philosophy", "Chemistry", "Physic", "Psychology", "Biology", "Archeology")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        autoCompleteTextView = binding.subjectEditText

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            autoCompleteTextView.setText(selectedItem)
        }

        autoCompleteTextView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                autoCompleteTextView.showDropDown()
            }
        }


        binding.btnRegister.setOnClickListener {
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome)
        }

        binding.btnLogin.setOnClickListener {
            AuthDialogUtils.showDialog(this, "Login to an existing account", false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AuthDialogUtils.RC_SIGN_IN) {
            AuthDialogUtils.handleSignInResult(data, { idToken ->
                Log.d("Subject", "idToken: $idToken")
            }, { exception ->
                Log.e("Subject", "Failed")
            })
        }

//        if (requestCode == AuthDialogUtils.RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                Toast.makeText(this, "${account.email} and ${account.idToken}", Toast.LENGTH_SHORT).show()
//            } catch (e: ApiException) {
//
//            }
//        }
    }
}