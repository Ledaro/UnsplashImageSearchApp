package com.example.unsplashimagesearchapp.ui.profile

import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.savedstate.SavedStateRegistryOwner
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SharedViewModel @AssistedInject constructor(
    private val unsplashRepository: UnsplashRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface SharedViewModelFactory {
        fun create(handle: SavedStateHandle): SharedViewModel
    }

    companion object {

        fun provideFactory(
            assistedFactory: SharedViewModelFactory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return assistedFactory.create(handle) as T
                }
            }
    }

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
