package com.bangkidss.scholarseeks.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("subject_area")
    fun subjectArea(
        @Body subjectAreaRequest: SubjectAreaRequest
    ): Call<List<SubjectAreaResponseItem>>

    @GET("search")
    suspend fun search(
        @Query("query") query: String,
        @Query("sort_by") sortBy: String? =  null,
        @Query("categories") categories: List<String>? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") per_page: Int? = null,
        @Header("Authorization") jwt_token: String
    ) : SearchResponse

    @POST("/content-model/get-articles")
    fun getRecommendationArticle(
        @Header("Authorization") jwt_token: String,
        @Body recommendationArticleRequest: RecomArticleRequest
    ) : Call<RecomArticleResponse>
}