package com.example.unsplashimagesearchapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashimagesearchapp.api.UnsplashApi
import com.example.unsplashimagesearchapp.data.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_START_PAGE_INDEX = 1

class UnsplashProfilePhotosPagingSource(
    private val unsplashApi: UnsplashApi,
    private val username: String,
) : PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_START_PAGE_INDEX

        return try {
            val photos = unsplashApi.searchProfilePhotos(username, position, params.loadSize)

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_START_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }
}