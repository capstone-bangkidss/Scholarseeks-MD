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
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException

object AuthDialogUtils {

    const val RC_SIGN_IN = 9001
    private lateinit var googleSignInClient: GoogleSignInClient

    fun showDialog(context: Context, title: String? = null, skip: Boolean) {
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

        val btnSkip: Button = dialogView.findViewById(R.id.btn_skip)
        btnSkip.setOnClickListener {
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            // login
            val signInIntent = googleSignInClient.signInIntent
            if (context is Activity) {
                context.startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

        btnRegister.setOnClickListener {
            // register
        }

        dialog.show()
    }

    fun handleSignInResult(data: Intent?, onSuccess: (idToken: String?) -> Unit, onFailure: (Exception) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            onSuccess(idToken)
        } catch (e: ApiException) {
            Log.e("authDialogutils", "Failed with code: ${e.message}, ${e.status}, ${e.statusCode}")
            onFailure(e)
        }
    }
}