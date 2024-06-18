package com.bangkidss.scholarseeks.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchResponse(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

@Parcelize
data class ArticlesItem(

	@field:SerializedName("article_id")
	val articleId: Int? = null,

	@field:SerializedName("index_keywords")
	val indexKeywords: String? = null,

	@field:SerializedName("references")
	val references: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("author_full_name")
	val authorFullName: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("abstract")
	val jsonMemberAbstract: String? = null,

	@field:SerializedName("cited_by")
	val citedBy: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("DOI")
	val dOI: String? = null,

	@field:SerializedName("authors")
	val authors: String? = null,

	@field:SerializedName("source_title")
	val sourceTitle: String? = null
) : Parcelable
