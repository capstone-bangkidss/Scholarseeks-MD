package com.bangkidss.scholarseeks.adapter

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.api.ArticlesItem
import com.bangkidss.scholarseeks.databinding.JournalCardBinding
import com.google.android.flexbox.FlexboxLayout
import java.lang.StringBuilder

class SearchAdapter(private val itemClickListener: (ArticlesItem) -> Unit) : PagingDataAdapter<ArticlesItem, SearchAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        val binding = JournalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
        val journal = getItem(position)
        if (journal != null){
            holder.bind(journal)
        }
    }

    class MyViewHolder(val binding: JournalCardBinding, private val itemClickListener: (ArticlesItem) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: ArticlesItem) {
            Log.d("viewList", "list created")
            val keywordContainer: FlexboxLayout = binding.keywordContainer
            keywordContainer.removeAllViews()
            binding.tvTitle.text = journal.title.toString()
            binding.tvAuthor.text = journal.authors.toString()
            binding.tvYear.text = journal.year.toString()
            val keywordString = journal.indexKeywords

            val keywords = keywordString?.split(";")?.map { it.trim() }
            keywords?.take(4)?.forEach { keyword ->
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
                oldItem.articleId == newItem.articleId


            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
                oldItem == newItem
        }
    }
}