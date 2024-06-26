package com.bangkidss.scholarseeks.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecomArticleResponse(

	@field:SerializedName("Response")
	val response: List<RecomArticleResponseItem>
)

@Parcelize
data class RecomArticleResponseItem(

	@field:SerializedName("article_id")
	val articleId: Int,

	@field:SerializedName("index_keywords")
	val indexKeywords: String,

	@field:SerializedName("year")
	val year: Int,

	@field:SerializedName("abstract")
	val abstract: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("DOI")
	val dOI: String,

	@field:SerializedName("authors")
	val authors: String,

	@field:SerializedName("rated_users")
	val ratedUsers: List<Int> = emptyList(),
) : Parcelable
