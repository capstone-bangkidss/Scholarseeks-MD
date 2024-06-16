package com.bangkidss.scholarseeks.api

data class SubjectAreaRequest(
    val subject_area: String,
    val user_id: String? = null
)
