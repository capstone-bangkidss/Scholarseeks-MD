package com.bangkidss.scholarseeks.api

data class RatingRequest(
    val article_rating: Int? = null,
    val article_id: String? = null,
    val user_id: String? = null
)
