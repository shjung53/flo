package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.Album
import com.example.flo.AlbumRVAdapter
import com.example.flo.BannerViewPagerAdapter
import com.example.flo.R
import com.example.flo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        albumDatas.apply {
            add(Album("Butter","방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Album("라일락","아이유 (IU)", R.drawable.img_album_exp2))
            add(Album( "Certified Lover Boy","Drake", R.drawable.img_clb))
            add(Album( "Stay","The Kid LAROI, Justin Bieber", R.drawable.img_stay))
            add(Album( "call on me","Josef Salvat", R.drawable.img_callonme))
            add(Album( "Take Care (Deluxe)","Drake", R.drawable.img_takecare))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)

        binding.homeAlbumsNewRv.adapter = albumRVAdapter

        binding.homeAlbumsNewRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)






        val bannerAdapter = BannerViewPagerAdapter(this)

        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        return binding.root


    }





}


