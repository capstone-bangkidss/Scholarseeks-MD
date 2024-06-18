package com.bangkidss.scholarseeks.ui.detailJournal

import android.content.Intent
import android.media.Rating
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.ApiService
import com.bangkidss.scholarseeks.api.RatingRequest
import com.bangkidss.scholarseeks.api.RatingResponse
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import com.bangkidss.scholarseeks.databinding.ActivityDetailJournalBinding
import com.bangkidss.scholarseeks.ui.webview.JournalSiteActivity
import com.google.android.flexbox.FlexboxLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailJournalBinding
    private lateinit var apiService: ApiService
    private lateinit var userPreference: UserPreference
    private lateinit var userModel: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailJournalBinding.inflate(layoutInflater)

        setContentView(binding.root)

        apiService = ApiConfig.getApiService()
        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Journal"

        val dataJournal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, RecomArticleResponseItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DETAIL)
        }

        if (dataJournal != null) {
            binding.tvTitle.text = dataJournal.title
            binding.tvDoi.text = "https://doi.org/${dataJournal.dOI}"
            binding.tvAuthor.text = dataJournal.authors
            binding.tvYear.text = dataJournal.year.toString()
            binding.tvAbstract.text = dataJournal.abstract
            
            binding.keywordContainer.removeAllViews()
            dataJournal.indexKeywords.split(";").forEach { keyword ->
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
        binding.ratingStar.setOnRatingBarChangeListener { _, rating, _, ->
            if (rating > 0) {
                binding.submitStar.visibility = View.VISIBLE
            } else {
                binding.submitStar.visibility = View.GONE
            }
        }
        binding.submitStar.setOnClickListener {
            val article_rating = binding.ratingStar.rating.toInt()
            val article_id = dataJournal?.articleId.toString()
            val user_id = userModel.user_id
            val jwt_token = userModel.jwt_token

            val ratingRequest = RatingRequest(article_rating, article_id, user_id)

            if (jwt_token != null) {
                apiService.rateArticle("Bearer $jwt_token", ratingRequest)
                    .enqueue(object : Callback<RatingResponse> {
                        override fun onResponse(call: Call<RatingResponse>, response: Response<RatingResponse>) {
                            Log.d("rating", "Response code: ${response.code()}")
                            Log.d("rating", "Response message: ${response.message()}")
                            Log.d("rating", "Response body: ${response.body()}")
                            if (response.body() != null) {
                                Toast.makeText(this@DetailJournalActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@DetailJournalActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                            Log.e("rating", "api call failed: $t")
                        }
                    })
            } else {
                Toast.makeText(this, "please restart application", Toast.LENGTH_SHORT).show()
            }
            binding.submitStar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}