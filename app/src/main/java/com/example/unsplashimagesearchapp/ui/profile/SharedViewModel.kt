package com.example.unsplashimagesearchapp.ui.profile

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SharedViewModel @AssistedInject constructor(
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
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return assistedFactory.create(handle) as T
                }
            }
    }
}
