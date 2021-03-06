package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.fragment.FileFragment
import com.example.flo.fragment.SaveAlbumFragment
import com.example.flo.fragment.SaveFragment

class LockerViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SaveFragment()
            1 -> FileFragment()
            else -> SaveAlbumFragment()
        }
    }
}