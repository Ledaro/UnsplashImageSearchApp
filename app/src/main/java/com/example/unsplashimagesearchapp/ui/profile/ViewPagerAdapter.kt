package com.example.unsplashimagesearchapp.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> ProfilePhotosFragment()
        1 -> ProfileLikedPhotosFragment()
        else -> ProfileCollectionsFragment()
    }
}