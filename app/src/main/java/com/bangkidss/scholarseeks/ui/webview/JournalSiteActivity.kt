package com.bangkidss.scholarseeks.ui.webview

import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.data_dummy.JournalRfy
import com.bangkidss.scholarseeks.databinding.ActivityJournalSiteBinding
import com.bangkidss.scholarseeks.ui.detailJournal.DetailJournalActivity

class JournalSiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalSiteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJournalSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataJournal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DetailJournalActivity.EXTRA_DETAIL, JournalRfy::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DetailJournalActivity.EXTRA_DETAIL)
        }

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Toast.makeText(this@JournalSiteActivity, "Successfully Moved to The Site", Toast.LENGTH_SHORT).show()
            }
        }

        binding.webView.webChromeClient = WebChromeClient()

        binding.webView.loadUrl(dataJournal?.doi.toString())
    }
}