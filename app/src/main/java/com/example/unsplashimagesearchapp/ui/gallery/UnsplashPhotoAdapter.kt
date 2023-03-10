package com.example.unsplashimagesearchapp.ui.gallery

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.unsplashimagesearchapp.R
import com.example.unsplashimagesearchapp.data.UnsplashPhoto
import com.example.unsplashimagesearchapp.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isLiked = false

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }

            binding.imageViewFavourite.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        animateFavouriteClick()
                        listener.onFavouriteClick(item.id, isLiked)
                    }
                }
            }

            binding.imageViewProfileImage.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        animateFavouriteClick()
                        listener.onProfileClick(item)
                    }
                }
            }

            binding.textViewUserName.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        animateFavouriteClick()
                        listener.onProfileClick(item)
                    }
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                Glide.with(imageViewProfileImage)
                    .load(photo.user.profile_image.medium)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewProfileImage)

                textViewUserName.text = photo.user.username

                isLiked = if (photo.liked_by_user) {
                    imageViewFavourite.setImageResource(R.drawable.ic_heart_filled)
                    true

                } else {
                    binding.imageViewFavourite.setImageResource(R.drawable.ic_heart_outline)
                    false

                }
            }
        }

        private fun animateFavouriteClick() {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = getItem(position)
                if (item != null) {
                    isLiked = !isLiked
                    isLiked = if (isLiked) {
                        binding.imageViewFavourite.setImageResource(R.drawable.ic_heart_filled)
                        true
                    } else {
                        binding.imageViewFavourite.setImageResource(R.drawable.ic_heart_outline)
                        false
                    }
                    val animator =
                        ObjectAnimator.ofArgb(
                            binding.imageViewFavourite,
                            "colorFilter",
                            Color.WHITE,
                            Color.WHITE
                        )
                    animator.duration = 500
                    animator.start()
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: UnsplashPhoto)
        fun onFavouriteClick(photoId: String, isFilled: Boolean)
        fun onProfileClick(photo: UnsplashPhoto)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }
}
