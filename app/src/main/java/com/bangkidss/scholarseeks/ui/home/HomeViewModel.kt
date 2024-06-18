package com.bangkidss.scholarseeks.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.RecomArticleRequest
import com.bangkidss.scholarseeks.api.RecomArticleResponse
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _recommendedArticles = MutableLiveData<List<RecomArticleResponseItem>>()
    val recommendedArticles: LiveData<List<RecomArticleResponseItem>> = _recommendedArticles

    private val _recomArticleisLoading = MutableLiveData<Boolean>()
    val recomArticleIsLoading: LiveData<Boolean> = _recomArticleisLoading

    fun getRecomendationArticle(jwtToken: String, userId: String) {
        _recomArticleisLoading.value = true
        val request = RecomArticleRequest(userId)
        val client = ApiConfig.getApiService().getRecommendationArticle(jwtToken, request)
        client.enqueue(object : Callback<List<RecomArticleResponseItem>> {
            override fun onResponse(
                call: Call<List<RecomArticleResponseItem>>,
                response: Response<List<RecomArticleResponseItem>>
            ) {
                _recomArticleisLoading.value = false
                if (response.isSuccessful) {
                    _recommendedArticles.value = response.body()
                    Log.d(TAG, "Articles fetched: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RecomArticleResponseItem>>, t: Throwable) {
                _recomArticleisLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
