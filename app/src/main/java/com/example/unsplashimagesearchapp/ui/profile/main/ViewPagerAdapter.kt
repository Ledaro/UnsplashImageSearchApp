package com.example.unsplashimagesearchapp.ui.profile.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unsplashimagesearchapp.ui.profile.ProfileLikedPhotosFragment
import com.example.unsplashimagesearchapp.ui.profile.ProfilePhotosFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ProfilePhotosFragment()
        else -> ProfileLikedPhotosFragment()
    }
}
