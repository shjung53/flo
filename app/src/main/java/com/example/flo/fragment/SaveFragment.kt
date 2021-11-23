package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.*
import com.example.flo.databinding.FragmentSaveBinding
import com.google.android.gms.common.GooglePlayServicesNotAvailableException

class SaveFragment : Fragment() {

    lateinit var binding : FragmentSaveBinding
    lateinit var songDB : SongDatabase
    private val songs = ArrayList<Song>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        songDB = SongDatabase.getInstance(requireContext())!!
        binding = FragmentSaveBinding.inflate(inflater, container, false)



        val saveRVAdapter = SaveRVAdapter()

        binding.saveListRcv.adapter = saveRVAdapter

        binding.saveListRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        saveRVAdapter.addSongs(songDB.SongDao().getLikedSongs(true) as ArrayList)

        saveRVAdapter.setMyItemClickListener(object: SaveRVAdapter.MyItemClickListener{
            override fun onRemoveSave(songId: Int) {
                songDB.SongDao().updateIsLikeById(false,songId)
            }
        })





        return binding.root
    }
    }