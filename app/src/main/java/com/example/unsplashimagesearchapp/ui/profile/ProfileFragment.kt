package com.example.unsplashimagesearchapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.databinding.FragmentProfileBinding
import com.example.unsplashimagesearchapp.ui.details.DetailsFragmentArgs
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val args by navArgs<ProfileFragmentArgs>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModel.SharedViewModelFactory

    private val viewModel: SharedViewModel by viewModels {
        SharedViewModel.provideFactory(sharedViewModelFactory, this, arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val photo = args.photo

        Glide.with(this)
            .load(photo.user.profile_image.large)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(binding.imageViewProfileImage)


        binding.apply {
            textViewUserName.text = photo.user.username
        }

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Tab $position"
        }.attach()
    }
}