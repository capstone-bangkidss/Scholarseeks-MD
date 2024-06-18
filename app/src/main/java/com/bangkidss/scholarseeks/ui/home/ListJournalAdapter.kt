package com.bangkidss.scholarseeks.ui.home

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import com.bangkidss.scholarseeks.databinding.JournalCardBinding
import com.bangkidss.scholarseeks.ui.detailJournal.DetailJournalActivity
import com.google.android.flexbox.FlexboxLayout

class ListJournalAdapter(private val listJournal: List<RecomArticleResponseItem>) :
    RecyclerView.Adapter<ListJournalAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: JournalCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JournalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listJournal.size.coerceAtMost(5)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val journal = listJournal[position]
        holder.binding.apply {
            tvTitle.text = journal.title
            tvAuthor.text = journal.authors
            tvYear.text = journal.year.toString()

            keywordContainer.removeAllViews()
            journal.indexKeywords.split(", ").take(4).forEach { keyword -> // Assuming indexKeywords is a comma-separated string
                val textView = TextView(root.context).apply {
                    text = keyword
                    layoutParams = FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 12, 8, 12)
                    }

                    setBackgroundResource(R.drawable.rounded_textview)
                    setPadding(16, 8, 16, 8)
                    typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                keywordContainer.addView(textView)
            }
        }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailJournalActivity::class.java)
            intentDetail.putExtra(DetailJournalActivity.EXTRA_DETAIL, journal)
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}