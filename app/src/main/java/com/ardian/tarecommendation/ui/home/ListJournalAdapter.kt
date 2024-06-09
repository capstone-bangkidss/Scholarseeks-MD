package com.ardian.tarecommendation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardian.tarecommendation.data_dummy.JournalRfy
import com.ardian.tarecommendation.databinding.JournalCardBinding

class ListJournalAdapter(private val listJournal: ArrayList<JournalRfy>) : RecyclerView.Adapter<ListJournalAdapter.ListViewHolder>() {
    inner class ListViewHolder(val binding: JournalCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JournalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listJournal.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val journal = listJournal[position]
        holder.binding.apply {
            tvJournalTitle.text = journal.title
            tvAuthor.text = journal.author
            tvJournalPublish.text = journal.published
//            for (category in journal.category!!) {
//                tvCategory.text = category
//            }
            tvCategory.text = journal.category?.joinToString(", ")
        }
    }
}