package com.bangkidss.scholarseeks.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkidss.scholarseeks.AuthDialogUtils
import com.bangkidss.scholarseeks.AuthResultCallback
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import com.bangkidss.scholarseeks.databinding.JournalCardBinding
import com.bangkidss.scholarseeks.ui.detailJournal.DetailJournalActivity
import com.google.android.flexbox.FlexboxLayout
import kotlin.random.Random

class ListJournalCollabAdapter(
    private val context: Context,
    private val googleSignInAccount: ActivityResultLauncher<Intent>,
    private val callback: AuthResultCallback,
    private val listJournal: List<RecomArticleResponseItem>
) :
    RecyclerView.Adapter<ListJournalCollabAdapter.ListViewHolder>() {

    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

    // Get colors from resources
    private val colors = arrayOf(
        R.color.colorOrange,
        R.color.colorGreen,
        R.color.colorLightBlue,
        R.color.colorPink,
        R.color.colorIndigo
    )

    inner class ListViewHolder(val binding: JournalCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JournalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mUserPreference = UserPreference(context)
        userModel = mUserPreference.getUser()
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listJournal.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val journal = listJournal[position]
        holder.binding.apply {
            tvTitle.text = journal.title
            tvAuthor.text = journal.authors
            tvYear.text = journal.year.toString()

            keywordContainer.removeAllViews()
            journal.indexKeywords.split(";").take(4).forEach { keyword ->
                keyword.let {
                    val textView = TextView(root.context).apply {
                        text = it
                        layoutParams = FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 12, 8, 12) // Equivalent to padding with top margin
                        }

                        val backgroundColor =
                            ContextCompat.getColor(context, colors[Random.nextInt(colors.size)])
                        val drawable = ContextCompat.getDrawable(
                            context,
                            R.drawable.rounded_textview
                        ) as GradientDrawable
                        drawable.setColor(backgroundColor)

                        background = drawable
                        setPadding(16, 8, 16, 8) // Padding
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium) // Font
                        setTextColor(ContextCompat.getColor(context, R.color.white)) // Text color
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f) // Text size in SP
                        textAlignment = View.TEXT_ALIGNMENT_CENTER // Text alignment
                    }
                    keywordContainer.addView(textView)
                }
            }
        }

        // melakukan perpindahan ke detail journal menggunakan intent
        holder.itemView.setOnClickListener {
            if (userModel.id_token.isNullOrEmpty()) {
                val dialogTitle = "Register for access"
                val skip = true
                AuthDialogUtils.showDialog(
                    context,
                    title = dialogTitle,
                    skip = skip,
                    signInResultLauncher = googleSignInAccount,
                    callback = callback
                )
            } else {

                val dataJournal = RecomArticleResponseItem(
                    articleId = journal.articleId ?: 0,
                    title = (journal.title) ?: "",
                    dOI = journal.dOI ?: "",
                    authors = journal.authors ?: "",
                    year = journal.year ?: 0,
                    abstract = journal.abstract ?: "",
                    indexKeywords = journal.indexKeywords ?: ""
                )

                val intentDetail =
                    Intent(holder.itemView.context, DetailJournalActivity::class.java)
                @Suppress("DEPRECATION")
                intentDetail.putExtra(
                    DetailJournalActivity.EXTRA_DETAIL,
                    dataJournal
                )
                holder.itemView.context.startActivity(intentDetail)
            }
        }
    }


}
