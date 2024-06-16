package com.bangkidss.scholarseeks.ui.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.ApiService
import com.bangkidss.scholarseeks.api.ArticlesItem
import com.bangkidss.scholarseeks.api.SearchRequest
import com.bangkidss.scholarseeks.api.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreViewModel(private val apiService: ApiService) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "The search result will displayed here"
    }
    val text: LiveData<String> = _text

    private val _searchResult = MutableLiveData<List<ArticlesItem>>()
    val searchResult: LiveData<List<ArticlesItem>> = _searchResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun search(query: String, sortBy: String? = null, categories: List<String>? = null, jwt_token: String): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(apiService, query, sortBy, categories, jwt_token)
            }
        ).flow.cachedIn(viewModelScope)
    }

//    fun search(query: String, sortBy: String? = null, categories: List<String>? = null, jwt_token: String) {
//        viewModelScope.launch {
//            try {
//                val response = ApiConfig.getApiService().search(query, sortBy, categories, 1, 3, jwt_token)
//                Log.d("response", response.toString())
//                val articles = response.articles?.filterNotNull() ?: emptyList()
//                _searchResult.postValue(articles)
//            } catch (e: Exception) {
//                _error.postValue(e.message)
//            }
//        }
//    }
}