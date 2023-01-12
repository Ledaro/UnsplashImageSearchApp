package com.example.unsplashimagesearchapp.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.data.UnsplashPhoto
import com.example.unsplashimagesearchapp.databinding.FragmentFavouritesBinding
import com.example.unsplashimagesearchapp.ui.gallery.GalleryFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment: Fragment(R.layout.fragment_favourites),
    FavouritesAdapter.OnItemClickListener {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favouriteViewModelFactory: FavouritesViewModel.FavouriteViewModelFactory

    private val viewModel: FavouritesViewModel by viewModels {
        FavouritesViewModel.provideFactory(favouriteViewModelFactory, this, arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        val adapter = FavouritesAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewModel.likedPhotos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

/*        adapter.addLoadStateListener { loadState ->
            binding.apply {
*//*                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error*//*
                progressBar.isVisible = false
                recyclerView.isVisible = true
                buttonRetry.isVisible = false
                textViewError.isVisible = false
            }
        }

        setHasOptionsMenu(true)
    }*/
}
    override fun onItemClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }

    override fun onFavouriteClick(photoId: String, isFilled: Boolean) {

    }

/*    override fun onFavouriteClick(photoId: String, isFilled: Boolean) {
        if (isFilled) {
            viewModel.likePhoto(photoId)

            viewModel.likePhotoResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Toast.makeText(context, "You've liked photo!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Ups, something went wrong :(", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            viewModel.unlikePhoto(photoId)

            viewModel.unlikePhotoResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Toast.makeText(context, "You've unliked photo!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Ups, something went wrong :(", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}