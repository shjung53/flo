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


//        플레이 변수
        val playing = "playing"
        val pause = "pause"


        if(intent.hasExtra("playing")){
            setPlayerStatus(isPlaying = true)
        }
        if(intent.hasExtra("pause")){
            setPlayerStatus(isPlaying = false)
        }


        binding.songBtnDownIb.setOnClickListener {
            finish()
        }
        binding.songBtnPlayIv.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            intent.putExtra(playing, playing)
            setPlayerStatus(false)
        }
        binding.songBtnPauseIv.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            intent.putExtra(pause, pause)
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



