package com.example.unsplashimagesearchapp.ui.gallery

import androidx.lifecycle.ViewModel
import com.example.unsplashimagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
): ViewModel() {
}
