package com.example.unsplashimagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.unsplashimagesearchapp.api.UnsplashApi
import com.example.unsplashimagesearchapp.data.pagination.UnsplashLikedPhotosPagingSource
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData

    fun getLikedPhotosSearchResults(username: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashLikedPhotosPagingSource(unsplashApi, username) }
        ).liveData

    suspend fun likePhoto(photoId: String): Response<Void> {
        return unsplashApi.likePhoto(photoId)
    }

    suspend fun unlikePhoto(photoId: String): Response<Void> {
        return unsplashApi.unlikePhoto(photoId)
    }
}