package com.ardian.tarecommendation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardian.tarecommendation.data_dummy.JournalRfy

class HomeViewModel : ViewModel() {

    private val _journalRecomIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _journalRecomIsLoading

    private val _listJournalRecom = MutableLiveData<List<JournalRfy>>()
    val listFollow: LiveData<List<JournalRfy>> = _listJournalRecom

    fun getJournalRecom() {
        _journalRecomIsLoading.value = true

    }
}