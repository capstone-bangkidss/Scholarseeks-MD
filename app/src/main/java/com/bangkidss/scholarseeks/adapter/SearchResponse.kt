package com.bangkidss.scholarseeks.adapter

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("journal")
	val journal: List<JournalItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class JournalItem(

	@field:SerializedName("author")
	val author: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("abstract")
	val abstract: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("keyword")
	val keyword: List<String?>? = null
)
