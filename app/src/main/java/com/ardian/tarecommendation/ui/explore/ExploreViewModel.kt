package com.ardian.tarecommendation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardian.tarecommendation.adapter.JournalItem

class ExploreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "The search result will displayed here"
    }
    val text: LiveData<String> = _text

    private val _sampleData = MutableLiveData<List<JournalItem>>()
    val sampleData: LiveData<List<JournalItem>> = _sampleData

    init {
        _sampleData.value = generateSampleData()
    }

    private fun generateSampleData(): List<JournalItem> {
        val sampleData = mutableListOf<JournalItem>()
        for (i in 1..10) {
            val keywords = listOf("Keyword 1", "Keyword 2", "Keyword 3")
            val item = JournalItem(
                id = "id868{$i}",
                title = "Version $i",
                author = listOf("chatgpt", "android"),
                year = 2009,
                keyword = listOf("coding", "begadang")
            )
            sampleData.add(item)
        }
        return sampleData
    }
}