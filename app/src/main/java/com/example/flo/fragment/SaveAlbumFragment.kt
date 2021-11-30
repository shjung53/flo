package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.*
import com.example.flo.databinding.FragmentSaveAlbumBinding

class SaveAlbumFragment: Fragment() {
    lateinit var binding: FragmentSaveAlbumBinding
    lateinit var albumDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveAlbumBinding.inflate(inflater, container, false)
        albumDB = SongDatabase.getInstance(requireContext())!!

        val userId = getUserIdx(requireContext())

        val saveAlbumRVAdapter = SaveAlbumRVAdapter()

        val likeAlbums = albumDB.AlbumDao().getLikeAlbums(userId)

        binding.saveAlbumListRcv.adapter = saveAlbumRVAdapter

        binding.saveAlbumListRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        if(likeAlbums.isNotEmpty()){saveAlbumRVAdapter.addAlbums(albumDB.AlbumDao().getLikeAlbums(userId) as ArrayList)}

        saveAlbumRVAdapter.setMyItemClickListener(object: SaveAlbumRVAdapter.MyItemClickListener{
            override fun onRemoveSave(albumId: Int) {
                albumDB.AlbumDao().disLikeAlbum(userId, albumId)
            }
        })


        return binding.root
    }
}
