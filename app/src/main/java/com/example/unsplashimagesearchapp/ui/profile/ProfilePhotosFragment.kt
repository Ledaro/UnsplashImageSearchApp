package com.example.unsplashimagesearchapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.data.UnsplashPhoto
import com.example.unsplashimagesearchapp.databinding.FragmentProfilePhotosBinding
import com.example.unsplashimagesearchapp.ui.profile.main.ProfileFragmentDirections
import com.example.unsplashimagesearchapp.ui.profile.main.ProfileUnsplashPhotoAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfilePhotosFragment : Fragment(R.layout.fragment_profile_photos),
    ProfileUnsplashPhotoAdapter.OnItemClickListener {
    private var _binding: FragmentProfilePhotosBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModel.SharedViewModelFactory

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModel.provideFactory(sharedViewModelFactory, this, arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfilePhotosBinding.bind(view)

        val adapter = ProfileUnsplashPhotoAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ProfileLoadStateAdapter { adapter.retry() },
                footer = ProfileLoadStateAdapter { adapter.retry() }
            )

            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.profilePhotos.observe(viewLifecycleOwner) {
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

    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action = ProfileFragmentDirections.actionProfileFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
