package com.bangkidss.scholarseeks.api

data class SearchRequest(
    val query: String,
    val sort_by: String? = null,
    val categories: List<String>? = null,
    val page: Int? = 1,
    val per_page: Int? = 5
)