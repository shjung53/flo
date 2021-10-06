package com.example.flo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songUpperTitleTv.text = intent.getStringExtra("title")
            binding.songUpperSingerTv.text = intent.getStringExtra("singer")
        }

//반복재생 설정
        binding.songBtnRepeatOffIv.setOnClickListener {
            binding.songBtnRepeatOffIv.visibility = View.GONE
            binding.songBtnRepeatOnIv.visibility = View.VISIBLE
        }
        binding.songBtnRepeatOnIv.setOnClickListener {
            binding.songBtnRepeatOnIv.visibility = View.GONE
            binding.songBtnRepeatOneIv.visibility = View.VISIBLE
        }
        binding.songBtnRepeatOneIv.setOnClickListener {
            binding.songBtnRepeatOneIv.visibility = View.GONE
            binding.songBtnRepeatListIv.visibility = View.VISIBLE
        }
        binding.songBtnRepeatListIv.setOnClickListener {
            binding.songBtnRepeatListIv.visibility = View.GONE
            binding.songBtnRepeatOffIv.visibility =View.VISIBLE
        }

//랜덤재생 설정
        binding.songRandomOffIv.setOnClickListener {
            binding.songRandomOffIv.visibility = View.GONE
            binding.songRandomOnIv.visibility = View.VISIBLE
        }
        binding.songRandomOnIv.setOnClickListener {
            binding.songRandomOnIv.visibility = View.GONE
            binding.songRandomOffIv.visibility = View.VISIBLE
        }



//        미니플레이어 연동
        if(intent.hasExtra("playing")){
               setPlayerStatus(false)
        }
        if(intent.hasExtra("pause")){
               setPlayerStatus(true)
        }

        binding.songBtnDownIb.setOnClickListener {
          onBackPressed()
        }
        binding.songBtnPlayIv.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            intent.putExtra("playing", "playing")
            setPlayerStatus(false)
            startActivity(intent)
        }
        binding.songBtnPauseIv.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            intent.putExtra("pause", "pause")
            setPlayerStatus(true)

        }

    }



        fun setPlayerStatus(isPlaying : Boolean){
            if(isPlaying){
                binding.songBtnPlayIv.visibility = View.VISIBLE
                binding.songBtnPauseIv.visibility = View.GONE
            }else{
                binding.songBtnPlayIv.visibility = View.GONE
                binding.songBtnPauseIv.visibility = View.VISIBLE
            }
        }



    }



