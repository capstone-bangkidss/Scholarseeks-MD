package com.bangkidss.scholarseeks

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.ApiService
import com.bangkidss.scholarseeks.api.AuthRequest
import com.bangkidss.scholarseeks.api.AuthResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthDialogUtils {

    const val RC_SIGN_IN = 9001
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: UserModel
    private lateinit var apiService: ApiService

    private var authResultCallback: AuthResultCallback? = null
    private var authDialog: AlertDialog? = null

    fun showDialog(context: Context, title: String? = null, skip: Boolean, signInResultLauncher: ActivityResultLauncher<Intent>, callback: AuthResultCallback) {

        userPreference = UserPreference(context)
        userModel = UserModel()
        apiService = ApiConfig.getApiService()
        authResultCallback = callback

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_auth, null)
        val btnRegister: Button = dialogView.findViewById(R.id.btn_signup)
        val btnLogin: Button = dialogView.findViewById(R.id.btn_signin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)

        if (!title.isNullOrEmpty()) {
            val titleTextView: TextView = dialogView.findViewById(R.id.tv_title)
            titleTextView.text = title
        }

        if (skip == false) {
            val divider: View = dialogView.findViewById(R.id.divider)
            val btnSkip: Button = dialogView.findViewById(R.id.btn_skip)
            divider.visibility = View.GONE
            btnSkip.visibility = View.GONE
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton(null, null)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        authDialog = dialog

        val btnSkip: Button = dialogView.findViewById(R.id.btn_skip)
        btnSkip.setOnClickListener {
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            // login
        }

        btnRegister.setOnClickListener {
            // register
            val signInIntent = googleSignInClient.signInIntent
            signInResultLauncher.launch(signInIntent)
        }

        dialog.show()
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            idToken?.let {
                sendTokenToApi(it)
            } ?: Log.e("authdialoutils", "id tioken is null")
        } catch (e: ApiException) {
            Log.e("authDialogutils", "Failed with code: ${e.message}, ${e.status}, ${e.statusCode}")
        }
    }

    private fun sendTokenToApi(idToken: String) {
        val user = userPreference.getUser()
        val user_id = user.user_id
        val jwt_token = user.jwt_token

        if (user_id != null && jwt_token != null) {
            val authRequest = AuthRequest(idToken, user_id)
            val call = apiService.authGoogle("Bearer $jwt_token", authRequest)
            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()
                        val jwtToken = authResponse?.jwtToken
                        val userId = authResponse?.user?.userId
                        if (jwtToken != null && userId != null) {
                            userModel.id_token = idToken
                            userModel.user_id = userId
                            userModel.jwt_token = jwtToken
                            userPreference.setUser(userModel)
                            Log.d("AuthDialogUtils", "UserModel updated: $userModel")
                            authResultCallback?.onAuthSuccess(userModel)
                            authDialog?.dismiss()
                        } else {
                            Log.e("AuthDialogUtils", "Invalid response data")
                            authResultCallback?.onAuthFailure()
                        }
                    } else {
                        Log.e("AuthDialogUtils", "API response unsuccessful: ${response.code()}")
                        authResultCallback?.onAuthFailure()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.e("AuthDialogUtils", "API call failed", t)
                    authResultCallback?.onAuthFailure()
                }
            })
        } else {
            Log.e("AuthDialogUtils", "User id or JWT token is null")
            authResultCallback?.onAuthFailure()
        }
    }

}