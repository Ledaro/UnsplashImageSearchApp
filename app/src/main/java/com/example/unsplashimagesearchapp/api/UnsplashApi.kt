package com.example.unsplashimagesearchapp.api

import com.example.unsplashimagesearchapp.BuildConfig
import retrofit2.Response
import retrofit2.http.*

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
        const val BEARER_TOKEN = BuildConfig.UNSPLASH_BEARER_TOKEN
    }

    //GET list of photos with public key
/*    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UnsplashResponse*/

    //GET list of photos with user bearer token
    @Headers("Accept-Version: v1", "Authorization: Bearer $BEARER_TOKEN")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UnsplashResponse

    @Headers("Accept-Version: v1", "Authorization: Bearer $BEARER_TOKEN")
    @POST("photos/{id}/like")
    suspend fun likePhoto(
        @Path("id") id: String
    ): Response<Void>

    @Headers("Accept-Version: v1", "Authorization: Bearer $BEARER_TOKEN")
    @DELETE("photos/{id}/like")
    suspend fun unlikePhoto(
        @Path("id") id: String
    ): Response<Void>
}
