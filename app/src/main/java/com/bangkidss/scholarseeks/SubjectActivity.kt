package com.bangkidss.scholarseeks

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.doOnPreDraw
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.SubjectAreaRequest
import com.bangkidss.scholarseeks.api.SubjectAreaResponse
import com.bangkidss.scholarseeks.api.SubjectAreaResponseItem
import com.bangkidss.scholarseeks.databinding.ActivitySubjectBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectActivity : AppCompatActivity(), AuthResultCallback {

    private lateinit var binding: ActivitySubjectBinding

    private lateinit var autoCompleteTextView: MaterialAutoCompleteTextView
    private val suggestions = listOf("Science", "Technology", "Medic", "Education", "Civilization", "Law", "Artificial Intelligent", "Machine Learning", "Decision Making System", "Game", "Android", "Cloud", "Programming", "Analyst", "Agriculture", "Music", "Movie", "Philosophy", "Chemistry", "Physic", "Psychology", "Biology", "Archeology")

    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

    private val googleSignInResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result from Google Sign-In
            AuthDialogUtils.handleSignInResult(result.data)
        } else {
            Log.e("YourFragment", "Google Sign-In failed")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserPreference = UserPreference(this)
        userModel = mUserPreference.getUser()

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
            val subjectArea = binding.subjectEditText.text.toString()
            if (!subjectArea.isNullOrEmpty()){
                val subjectAreaRequest = SubjectAreaRequest(subjectArea)
                val client = ApiConfig.getApiService().subjectArea(subjectAreaRequest)

                client.enqueue(object : Callback<List<SubjectAreaResponseItem>> {
                    override fun onResponse(call: Call<List<SubjectAreaResponseItem>>, response: Response<List<SubjectAreaResponseItem>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && responseBody.isNotEmpty()) {
                                var jwtToken: String? = null
                                var userId: String? = null

                                for (item in responseBody) {
                                    if (item.jwtToken != null) {
                                        jwtToken = item.jwtToken
                                    } else if (item.user != null) {
                                        userId = item.user?.userId
                                    }
                                }
                                Log.d("responseSubjectArea", "$userId - $jwtToken")

                                processResponse(jwtToken, userId)
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<SubjectAreaResponseItem>>, t: Throwable) {
                        Log.e("message", "onFailureSubjectArea: ${t.message}")
                    }
                })
            }
        }

        binding.btnLogin.setOnClickListener {
            AuthDialogUtils.showDialog(this, "Login to an existing account", true, signInResultLauncher = googleSignInResultLauncher, callback = this@SubjectActivity)
        }
    }

    private fun processResponse(jwt_token: String?, user_id: String?) {
        val userPreference = UserPreference(this)
        val userModel = UserModel()

        userModel.user_id = user_id
        userModel.jwt_token = jwt_token

        userPreference.setUser(userModel)
        Toast.makeText(this, "berhasil mengirim subject area $user_id and $jwt_token", Toast.LENGTH_SHORT).show()


        val intentHome = Intent(this, HomeActivity::class.java)
        startActivity(intentHome)

    }

    override fun onAuthSuccess(userModel: UserModel) {
        this.userModel = userModel
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onAuthFailure() {
        Log.e("ExploreFragment", "Auth failure")
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == AuthDialogUtils.RC_SIGN_IN) {
//            AuthDialogUtils.handleSignInResult(data, { idToken ->
//                Log.d("Subject", "idToken: $idToken")
//            }, { exception ->
//                Log.e("Subject", "Failed")
//            })
//        }

//        if (requestCode == AuthDialogUtils.RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                Toast.makeText(this, "${account.email} and ${account.idToken}", Toast.LENGTH_SHORT).show()
//            } catch (e: ApiException) {
//
//            }
//        }
//    }
}