package com.bangkidss.scholarseeks.ui.ratedArticle

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.RatingRequest
import com.bangkidss.scholarseeks.api.RatingResponse
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import com.bangkidss.scholarseeks.api.UserId
import com.bangkidss.scholarseeks.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatedArticleViewModel : ViewModel() {

    private val _ratedArticles = MutableLiveData<List<RecomArticleResponseItem>>()
    val ratedArticles: LiveData<List<RecomArticleResponseItem>> = _ratedArticles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getRatedArticles(jwtToken: String, userId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRatedArticle("Bearer $jwtToken", userId)
        client.enqueue(object : Callback<List<RecomArticleResponseItem>> {
            override fun onResponse(
                call: Call<List<RecomArticleResponseItem>>,
                response: Response<List<RecomArticleResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _ratedArticles.value = response.body()
                    Log.d(TAG, "Rated Article Success: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RecomArticleResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun unrateArticles(jwtToken: String, userId: String, article_id: String) {
        val ratingRequest = RatingRequest(null, article_id, userId)
        val client = ApiConfig.getApiService().unrateArticle("Bearer $jwtToken", ratingRequest)
        client.enqueue(object : Callback<RatingResponse> {
            override fun onResponse(
                call: Call<RatingResponse>,
                response: Response<RatingResponse>
            ) {
                getRatedArticles(jwtToken, userId)
            }

            override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }



    companion object {
        private const val TAG = "RatedArticleViewModel"
    }
}
