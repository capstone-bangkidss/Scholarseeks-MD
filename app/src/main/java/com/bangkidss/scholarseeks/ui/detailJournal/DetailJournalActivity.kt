package com.bangkidss.scholarseeks.ui.detailJournal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.data_dummy.JournalRfy
import com.bangkidss.scholarseeks.databinding.ActivityDetailJournalBinding
import com.bangkidss.scholarseeks.ui.webview.JournalSiteActivity
import com.google.android.flexbox.FlexboxLayout

class DetailJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailJournalBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Journal"

        val dataJournal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, JournalRfy::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DETAIL)
        }

        if (dataJournal != null) {
            binding.tvTitle.text = dataJournal.title
            binding.tvDoi.text = dataJournal.doi
            binding.tvAuthor.text = dataJournal.author
            binding.tvYear.text = dataJournal.published
            binding.tvAbstract.text = dataJournal.abstract

            binding.keywordContainer.removeAllViews()
            dataJournal.keyword?.forEach { keyword ->
                keyword.let {
                    val textView = TextView(binding.root.context).apply {
                        text = it
                        layoutParams = FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 12, 8, 12) // Equivalent to padding with top margin
                        }

                        setBackgroundResource(R.drawable.rounded_textview) // Background resource
                        setPadding(16, 8, 16, 8) // Padding
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium) // Font
                        setTextColor(ContextCompat.getColor(context, R.color.white)) // Text color
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f) // Text size in SP
                        textAlignment = View.TEXT_ALIGNMENT_CENTER // Text alignment
                    }
                    binding.keywordContainer.addView(textView)
                }
            }
        }

        binding.btnVisitSite.setOnClickListener {
            val intentVisitSite = Intent(this, JournalSiteActivity::class.java)
            intentVisitSite.putExtra(EXTRA_DETAIL, dataJournal)
            startActivity(intentVisitSite)
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}