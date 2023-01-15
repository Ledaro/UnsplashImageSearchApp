package com.example.unsplashimagesearchapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.data.UnsplashPhoto
import com.example.unsplashimagesearchapp.databinding.FragmentDetailsBinding
import com.example.unsplashimagesearchapp.ui.favourites.FavouritesFragmentDirections

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = photo.description != null

                        Glide.with(this@DetailsFragment)
                            .load(photo.user.profile_image.medium)
                            .circleCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_error)
                            .into(imageViewProfileImage)

                        return false
                    }

                })
                .into(imageView)

            textViewCreator.text = photo.user.username

            if (photo.description.isNullOrEmpty()) {
                textViewDescription.text = "No description"

            } else textViewDescription.text = photo.description

            imageViewProfileImage.setOnClickListener {
                onProfileClicked(photo)
            }

            textViewCreator.setOnClickListener {
                onProfileClicked(photo)
            }
        }
    }

    private fun onProfileClicked(photo: UnsplashPhoto) {
        val action = DetailsFragmentDirections.actionDetailsFragmentToProfileFragment(photo)
        findNavController().navigate(action)
    }
}