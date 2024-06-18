package com.bangkidss.scholarseeks.ui.home

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.data_dummy.JournalCollab
import com.bangkidss.scholarseeks.databinding.JournalCardBinding
import com.google.android.flexbox.FlexboxLayout

class ListJournalCollabAdapter(private val listJournal: ArrayList<JournalCollab>) : RecyclerView.Adapter<ListJournalCollabAdapter.ListViewHolder>() {
    class ListViewHolder(val binding: JournalCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JournalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listJournal.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val journal = listJournal[position]
        holder.binding.apply {
            tvTitle.text = journal.title
            tvAuthor.text = journal.author
            tvYear.text = journal.published

            keywordContainer.removeAllViews()
            journal.keyword?.take(4)?.forEach { keyword ->
                keyword.let {
                    val textView = TextView(root.context).apply {
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
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f) // Text size in SP
                        textAlignment = View.TEXT_ALIGNMENT_CENTER // Text alignment
                    }
                    keywordContainer.addView(textView)
                }
            }
        }
    }


}
