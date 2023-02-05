package com.example.unsplashimagesearchapp.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository,
) : ViewModel() {

    private val user: String = "ledaro"

    private val currentUser = MutableLiveData(user)

    val likedPhotos = currentUser.switchMap { user ->
        unsplashRepository.getLikedPhotosSearchResults(user).cachedIn(viewModelScope)
    }

    fun searchLikedPhotos() {
        currentUser.value = user
    }
}
