package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding : FragmentSongBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root

        //토스트메세지
        binding.albumBtnSongPlayIv.setOnClickListener {
            var tMessage = Toast.makeText(requireActivity(), "한곡이 재생목록에 담겼습니다\n중복곡은 제외됩니다", Toast.LENGTH_SHORT)
            tMessage.show()
        }
    }

}
