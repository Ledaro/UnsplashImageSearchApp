package com.example.unsplashimagesearchapp.ui.gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository,
    state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "dogs"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    private val likePhotoResponse: MutableLiveData<Response<Void>> = MutableLiveData()
    private val unlikePhotoResponse: MutableLiveData<Response<Void>> = MutableLiveData()

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
