package com.ardian.tarecommendation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

object AuthDialogUtils {
    fun showDialog(context: Context, title: String? = null, skip: Boolean) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_auth, null)
        val btnLogin: Button = dialogView.findViewById(R.id.btn_google)
        val btnRegister: Button = dialogView.findViewById(R.id.btn_register)

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
        }

        btnRegister.setOnClickListener {
            // register
        }

        dialog.show()
    }
}