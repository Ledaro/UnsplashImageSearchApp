package com.example.unsplashimagesearchapp.ui.gallery

import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.savedstate.SavedStateRegistryOwner
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import retrofit2.Response

class GalleryViewModel @AssistedInject constructor(
    private val unsplashRepository: UnsplashRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface GalleryViewModelFactory {
        fun create(handle: SavedStateHandle): GalleryViewModel
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "dogs"

        fun provideFactory(
            assistedFactory: GalleryViewModelFactory,
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

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    val likePhotoResponse: MutableLiveData<Response<Void>> = MutableLiveData()
    val unlikePhotoResponse: MutableLiveData<Response<Void>> = MutableLiveData()

    val photos = currentQuery.switchMap { queryString ->
        unsplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    fun likePhoto(id: String) {
        viewModelScope.launch {
            likePhotoResponse.value = unsplashRepository.likePhoto(id)
        }
    }

    fun unlikePhoto(id: String) {
        viewModelScope.launch {
            unlikePhotoResponse.value = unsplashRepository.unlikePhoto(id)
        }
    }
}
