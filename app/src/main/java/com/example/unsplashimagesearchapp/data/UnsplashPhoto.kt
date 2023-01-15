package com.example.unsplashimagesearchapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser,
    val liked_by_user: Boolean
) : Parcelable {
    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String,
        val profile_image: UnsplashUserProfileImage,
        val bio: String?,
        val location: String?,
        val instagram_username: String?,
        val twitter_username: String?

    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }

    @Parcelize
    data class UnsplashUserProfileImage(
        val small: String,
        val medium: String,
        val large: String,
    ) : Parcelable
}
