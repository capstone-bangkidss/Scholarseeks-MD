package com.bangkidss.scholarseeks.ui.explore

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkidss.scholarseeks.api.ApiService
import com.bangkidss.scholarseeks.api.ArticlesItem

class SearchPagingSource(
    private val apiService: ApiService,
    private val query: String,
    private val sortBy: String?,
    private val categories: List<String>?,
    private val jwt_token: String
) : PagingSource<Int, ArticlesItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val page = params.key ?: 1
            val response =
                apiService.search(query, sortBy, categories, page, params.loadSize, jwt_token)
            val articles = response.articles?.filterNotNull() ?: emptyList()
            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}