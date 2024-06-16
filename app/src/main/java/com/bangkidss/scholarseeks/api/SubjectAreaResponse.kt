package com.bangkidss.scholarseeks.api

import com.google.gson.annotations.SerializedName

data class SubjectAreaResponse(

	@field:SerializedName("SubjectAreaResponse")
	val subjectAreaResponse: List<SubjectAreaResponseItem?>? = null
)

data class SubjectAreaResponseItem(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("jwt_token")
	val jwtToken: String? = null
)

data class User(

	@field:SerializedName("rated_articles")
	val ratedArticles: List<Any?>? = null,

	@field:SerializedName("subject_area")
	val subjectArea: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
