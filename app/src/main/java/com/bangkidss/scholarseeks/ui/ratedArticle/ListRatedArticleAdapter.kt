package com.bangkidss.scholarseeks.ui.ratedArticle

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import com.bangkidss.scholarseeks.databinding.JournalCardBinding
import com.bangkidss.scholarseeks.ui.detailJournal.DetailJournalActivity
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class ListRatedArticleAdapter(
    private val context: Context,
    private val viewModel: RatedArticleViewModel,
    private var listMyRatingArticle: List<RecomArticleResponseItem>
) : RecyclerView.Adapter<ListRatedArticleAdapter.ListViewHolder>() {

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

    override fun getItemCount(): Int = listMyRatingArticle.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val journal = listMyRatingArticle[position]
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
            val jwtToken = userModel.jwt_token ?: ""
            val user_id = userModel.user_id ?: ""
            iconButton.visibility = View.VISIBLE
            iconButton.setOnClickListener {
                viewModel.unrateArticles(jwtToken, user_id, journal.articleId.toString())
                notifyItemRemoved(holder.adapterPosition)
            }

        }
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailJournalActivity::class.java)
            @Suppress("DEPRECATION")
            intentDetail.putExtra(
                DetailJournalActivity.EXTRA_DETAIL,
                listMyRatingArticle[holder.adapterPosition]
            )
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    fun updateArticles(newArticles: List<RecomArticleResponseItem>) {
        listMyRatingArticle = newArticles
        notifyDataSetChanged()
    }
}
