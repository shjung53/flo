package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.AlbumViewPagerAdapter
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBtnBackIb.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }





        val albumAdapter = AlbumViewPagerAdapter(this)
        binding.albumAlbumInfoVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumInfoTl, binding.albumAlbumInfoVp){
            tab, position ->
            tab.text = information[position]
        }.attach()


        return binding.root


    }

}
