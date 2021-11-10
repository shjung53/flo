package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.*
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson


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

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{

            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }
        })


//        레이아웃 매니저
        binding.homeAlbumsNewRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)








        val bannerAdapter = BannerViewPagerAdapter(this)

        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        return binding.root


    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }


}


