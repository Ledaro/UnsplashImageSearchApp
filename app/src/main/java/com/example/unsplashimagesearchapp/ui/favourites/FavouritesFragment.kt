package com.example.unsplashimagesearchapp.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.data.UnsplashPhoto
import com.example.unsplashimagesearchapp.databinding.FragmentFavouritesBinding
import com.example.unsplashimagesearchapp.ui.gallery.GalleryFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites),
    FavouritesUnsplashPhotoAdapter.OnItemClickListener {
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

        val adapter = FavouritesUnsplashPhotoAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = FavouritesLoadStateAdapter { adapter.retry() },
                footer = FavouritesLoadStateAdapter { adapter.retry() }
            )

            buttonRetry.setOnClickListener {
                adapter.retry()
            }

            swiperefresh.setOnRefreshListener {
                viewModel.searchLikedPhotos()

                swiperefresh.isRefreshing = false
            }
        }

        viewModel.likedPhotos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
            }
        }

        viewModel.searchLikedPhotos()
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }

    override fun onFavouriteClick(photoId: String, isFilled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onProfileClick(photo: UnsplashPhoto) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToProfileFragment(photo)
        val args = action.arguments
        args.putString("label", photo.user.username)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
