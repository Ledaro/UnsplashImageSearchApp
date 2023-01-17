package com.example.unsplashimagesearchapp.ui.profile.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.databinding.FragmentProfileBinding
import com.example.unsplashimagesearchapp.ui.profile.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val args by navArgs<ProfileFragmentArgs>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModel.SharedViewModelFactory

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModel.provideFactory(sharedViewModelFactory, this, arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val photo = args.photo

        (requireActivity() as AppCompatActivity).supportActionBar?.title = photo.user.username

        Glide.with(this)
            .load(photo.user.profile_image.large)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(binding.imageViewProfileImage)

        binding.apply {
            textViewUserName.text = photo.user.name

            if (photo.user.location == null) {
                textViewLocation.text = "Unknown"
            } else {
                textViewLocation.text = photo.user.location
            }

            if (photo.user.bio == null) {
                textViewBio.text = "Unknown"
            } else {
                textViewBio.text = photo.user.bio
            }

            if (photo.user.instagram_username == null) {
                textViewInstagram.text = "Unknown"
            } else {
                textViewInstagram.text = "@" + photo.user.instagram_username
            }

            if (photo.user.twitter_username == null) {
                textViewTwitter.text = "Unknown"
            } else {
                textViewTwitter.text = "@" + photo.user.twitter_username
            }

            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewUnsplash.apply {
                text = "Photo by ${photo.user.name}"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "PHOTOS"
                1 -> "LIKES"
                else -> "COLLECTIONS"
            }
        }.attach()

        viewModel.setProfile(photo.user.username)
    }
}
