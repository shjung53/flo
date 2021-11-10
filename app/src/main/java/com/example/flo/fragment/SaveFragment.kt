package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.Album
import com.example.flo.R
import com.example.flo.Save
import com.example.flo.SaveRVAdapter
import com.example.flo.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {

    lateinit var binding : FragmentSaveBinding
    private var saveDatas = ArrayList<Save>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveBinding.inflate(inflater, container, false)


        saveDatas.apply {
            add(Save("Butter","방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Save("라일락","아이유 (IU)", R.drawable.img_album_exp2))
            add(Save( "Certified Lover Boy","Drake", R.drawable.img_clb))
            add(Save( "Stay","The Kid LAROI, Justin Bieber", R.drawable.img_stay))
            add(Save( "call on me","Josef Salvat", R.drawable.img_callonme))
            add(Save( "Take Care (Deluxe)","Drake", R.drawable.img_takecare))
        }


        val saveRVAdapter = SaveRVAdapter(saveDatas)

        binding.saveListRcv.adapter = saveRVAdapter

        binding.saveListRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)





        return binding.root
    }
    }