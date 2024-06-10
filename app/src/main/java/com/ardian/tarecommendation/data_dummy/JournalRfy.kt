package com.ardian.tarecommendation.data_dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JournalRfy(
    val title: String?,
    val author: String?,
    val published: String?,
    val keyword: ArrayList<String>?
) : Parcelable

fun getJournal(): List<JournalRfy> {
    return listOf(
        JournalRfy(
            "Learning Analytics: Game-based Learning for Programming Course in Higher Education",
            "Priyaadharshini M, Natha Mayil N, R Dakshina, Sandhya S., Bettina Shirley R",
            "19 June 2020",
            (arrayListOf("Game Based Learning", "Learning", "Programming", "Higher Education", "Programming"))
        ),
        JournalRfy(
            "Klasifikasi Gambar Menggunakan VGG16",
            "Arya, Furqon, Akbar",
            "17 Agustus 2021",
            (arrayListOf("Machine Learning", "Deep Learning", "Programming"))
        )
    )
}
