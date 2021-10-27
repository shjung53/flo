package com.example.flo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {


    private lateinit var player: Player

    private val song: Song = Song()


    lateinit var binding: ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        player = Player(song.playTime, song.isPlaying)
        player.start()



//        노래 렌더링


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
            binding.songBtnRepeatOffIv.visibility = View.VISIBLE
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





        binding.songBtnDownIb.setOnClickListener {
            finish()
        }

        binding.songBtnPlayIv.setOnClickListener {
            player.isPlaying = true
            setPlayerStatus(false)
        }

        binding.songBtnPauseIv.setOnClickListener {
            player.isPlaying = false
            setPlayerStatus(true)

        }

    }


    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("playTime") && intent.hasExtra(
                "isPlaying"
            )
        ) {
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.playTime = intent.getIntExtra("playTime", 0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)

            binding.songTimeEndTv.text = String.format("%02d:%02d", song.playTime/60,song.playTime%60)
            binding.songUpperTitleTv.text = song.title
            binding.songUpperSingerTv.text = song.singer
            setPlayerStatus(song.isPlaying)
        }
    }


    fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
        } else {
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        }
    }


    inner class Player(private val playTime: Int, var isPlaying: Boolean) : Thread(){
        private var second = 0

        override fun run() {
            try {
                while (true) {

                    if (second >= playTime){
                        break
                    }
                    if (isPlaying){
                        sleep(1000)
                        second++

                        runOnUiThread {
                            binding.songProgressSb.progress = second* 1000 / playTime
                            binding.songTimeStartTv.text =
                                String.format("%02d:%02d",second/60,second%60)
                        }

                    }
                }

            }catch (e : InterruptedException){
                Log.d("interrupt", "스레드가 종료")
            }


            }
        }

    override fun onDestroy(){
        super.onDestroy()
        player.interrupt()
    }



}






