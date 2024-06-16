package com.bangkidss.scholarseeks.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("subject_area")
    fun subjectArea(
        @Body subjectAreaRequest: SubjectAreaRequest
    ): Call<List<SubjectAreaResponseItem>>
}