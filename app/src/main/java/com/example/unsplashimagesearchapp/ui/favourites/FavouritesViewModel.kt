package com.example.unsplashimagesearchapp.ui.favourites

import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.savedstate.SavedStateRegistryOwner
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FavouritesViewModel @AssistedInject constructor(
    private val unsplashRepository: UnsplashRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface FavouriteViewModelFactory {
        fun create(handle: SavedStateHandle): FavouritesViewModel
    }

    companion object {
        private const val USER = "ledaro"

        fun provideFactory(
            assistedFactory: FavouriteViewModelFactory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return assistedFactory.create(handle) as T
                }
            }
    }

    private val currentUser = MutableLiveData(USER)

    val likedPhotos = currentUser.switchMap { user ->
        unsplashRepository.getLikedPhotosSearchResults(user).cachedIn(viewModelScope)
    }

    val test = likedPhotos
}