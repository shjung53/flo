package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.Album
import com.example.flo.SaveAlbumRVAdapter
import com.example.flo.SaveRVAdapter
import com.example.flo.SongDatabase
import com.example.flo.databinding.FragmentSaveAlbumBinding

class SaveAlbumFragment: Fragment() {
    lateinit var binding: FragmentSaveAlbumBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveAlbumBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!


        val userId = getJwt()

        val saveAlbumRVAdapter = SaveAlbumRVAdapter()

        binding.saveAlbumListRcv.adapter = saveAlbumRVAdapter

        binding.saveAlbumListRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        saveAlbumRVAdapter.addAlbums(songDB.AlbumDao().getLikeAlbums(userId) as ArrayList)

        saveAlbumRVAdapter.setMyItemClickListener(object: SaveAlbumRVAdapter.MyItemClickListener{
            override fun onRemoveSave(albumId: Int) {
                songDB.AlbumDao().disLikeAlbum(userId, albumId)
            }
        })


        return binding.root
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt", 0)
    }
}
