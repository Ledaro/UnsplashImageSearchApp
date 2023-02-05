package com.example.unsplashimagesearchapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository,
) : ViewModel() {

    private val currentProfile = MutableLiveData("")

    val profilePhotos = currentProfile.switchMap { user ->
        unsplashRepository.getProfilePhotosResults(user).cachedIn(viewModelScope)
    }

    val likedPhotos = currentProfile.switchMap { user ->
        unsplashRepository.getLikedPhotosSearchResults(user).cachedIn(viewModelScope)
    }

    fun setProfile(username: String) {
        currentProfile.value = username
    }
}
