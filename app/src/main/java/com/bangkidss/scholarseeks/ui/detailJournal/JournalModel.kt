package com.bangkidss.scholarseeks.ui.detailJournal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JournalModel(
    val title: String?,
    val authors: String?,
    val DOI: String?,
    val year: Int?,
    val abstract: String?,
    val index_keywords: String?
) : Parcelable
