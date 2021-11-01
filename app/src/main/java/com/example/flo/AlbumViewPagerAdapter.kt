package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.fragment.DetailFragment
import com.example.flo.fragment.MediaFragment
import com.example.flo.fragment.SongFragment

class AlbumViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> MediaFragment()
        }

    }
}