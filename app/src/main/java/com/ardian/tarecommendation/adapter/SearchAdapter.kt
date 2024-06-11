package com.ardian.tarecommendation.adapter

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardian.tarecommendation.R
import com.ardian.tarecommendation.databinding.JournalCardBinding
import com.google.android.flexbox.FlexboxLayout
import java.lang.StringBuilder

class SearchAdapter(private val itemClickListener: (JournalItem) -> Unit) : ListAdapter<JournalItem, SearchAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        val binding = JournalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
        val journal = getItem(position)
        holder.bind(journal)
    }

    class MyViewHolder(val binding: JournalCardBinding, private val itemClickListener: (JournalItem) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: JournalItem) {
            val authorList: List<String?>? = journal.author
            val authorText = StringBuilder()
            if (!authorList.isNullOrEmpty()) {
                authorList.forEach { author ->
                    author?.let {
                        authorText.append(it).append(", ")
                    }
                }
                // hapus koma
                if (authorText.isNotEmpty()) {
                    authorText.setLength(authorText.length - 2)
                }
            }
            val keywordContainer: FlexboxLayout = binding.keywordContainer
            keywordContainer.removeAllViews()
            binding.tvTitle.text = journal.title
            binding.tvAuthor.text = authorText.toString()
            binding.tvYear.text = journal.year.toString()
            journal.keyword?.forEach { keyword ->
                keyword?.let {
                    val textView = TextView(binding.root.context).apply {
                        text = it
                        layoutParams = FlexboxLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 12, 8, 12) // Equivalent to padding
                        }
                        setBackgroundResource(R.drawable.rounded_textview) // Background resource
                        setPadding(16, 8, 16, 8) // Padding
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium) // Font
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f) // Text size in SP
                        setTextColor(ContextCompat.getColor(context, R.color.white)) // Text color
                        textAlignment = View.TEXT_ALIGNMENT_CENTER // Text alignment
                    }
                    keywordContainer.addView(textView)
                }
            }

            binding.root.setOnClickListener {
                itemClickListener(journal)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JournalItem>() {
            override fun areItemsTheSame(oldItem: JournalItem, newItem: JournalItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: JournalItem, newItem: JournalItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}