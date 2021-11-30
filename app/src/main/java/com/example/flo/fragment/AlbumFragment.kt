package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.*
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    val information = arrayListOf("수록곡", "상세정보", "영상")

    val gson = Gson()

    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

//        홈에서 넘어온 데이터 받아오기
        val albumData = arguments?.getString("album")
        val album = gson.fromJson(albumData, Album::class.java)

        isLiked = isLikedAlbum(album.id)

        setView(album)
        setClickListeners(album)


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

    private fun setView(album: Album) {
        binding.albumCoverIv.setImageResource(album.coverImg!!)
        binding.albumTitleTv.text = album.title
        binding.albumSingerTv.text = album.singer

        if(isLiked){
            binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setClickListeners(album: Album) {
        val userId: Int = getUserIdx(requireContext())

        binding.albumBtnLikeOffIv.setOnClickListener {
            if(isLiked){
                binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(userId, album.id)
            }else{
                binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }



    private fun likeAlbum(userId: Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.AlbumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId: Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getUserIdx(requireContext())

        val likeId: Int? = songDB.AlbumDao().islikeAlbum(userId, albumId)

        return likeId != null
    }

    private fun disLikeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        songDB.AlbumDao().disLikeAlbum(userId, albumId)
    }


}
