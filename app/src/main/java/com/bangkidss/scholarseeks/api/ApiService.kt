package com.bangkidss.scholarseeks.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
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

    @POST("auth/google")
    fun authGoogle(
        @Header("Authorization") jwt_token: String?,
        @Body authRequest: AuthRequest
    ) : Call<AuthResponse>

    @POST("articles/rating")
    fun rateArticle(
        @Header("Authorization") jwt_token: String,
        @Body ratingRequest: RatingRequest
    ) : Call<RatingResponse>

    @HTTP(method = "DELETE", path = "articles/rating", hasBody = true)
    fun unrateArticle(
        @Header("Authorization") jwt_token: String,
//        @Query("article_id") article_id: String,
//        @Query("user_id") user_id: String,
        @Body ratingRequest: RatingRequest
    ) : Call<RatingResponse>


    @POST("/content-model/get-articles")
    fun getRecommendationArticle(
        @Header("Authorization") jwt_token: String,
        @Body userId: UserId
    ) : Call<List<RecomArticleResponseItem>>

    @POST("/collaborative-model/get-articles")
    fun getCollaborativeArticle(
        @Header("Authorization") jwt_token: String,
        @Body userId: UserId
    ) : Call<List<RecomArticleResponseItem>>

    @GET("articles/rating/{user_id}")
    fun getRatedArticle(
        @Header("Authorization") jwt_token: String,
        @Path("user_id") userId: String
    ) : Call<List<RecomArticleResponseItem>>
}