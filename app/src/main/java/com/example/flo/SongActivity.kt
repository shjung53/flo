package com.example.flo

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
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


    private lateinit var timer: Timer

    private val song: Song = Song()

    private  var mediaPlayer : MediaPlayer? = null


    lateinit var binding: ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initSong()

        timer = Timer(song.playTime, song.isPlaying)
        timer.start()


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
            song.isPlaying = true
            timer.isPlaying = true
            setPlayerStatus(song)
            mediaPlayer?.start()
        }

        binding.songBtnPauseIv.setOnClickListener {
            song.isPlaying = false
            timer.isPlaying = false
            setPlayerStatus(song)
            mediaPlayer?.pause()
        }

    }


    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("second") && intent.hasExtra("playTime")
            && intent.hasExtra("isPlaying") && intent.hasExtra("music"))
         {
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.second = intent.getIntExtra("second", 0)
            song.playTime = intent.getIntExtra("playTime", 0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            song.music = intent.getStringExtra("Music")!!
            val music = resources.getIdentifier(song.music, "raw",this.packageName)  // 리소스폴더의 파일을 패키지로 불러옴



            binding.songTimeEndTv.text = String.format("%02d:%02d", song.playTime/60,song.playTime%60)
            binding.songUpperTitleTv.text = song.title
            binding.songUpperSingerTv.text = song.singer
             setPlayerStatus(song)
             mediaPlayer = MediaPlayer.create(this, music)  // 미디어 플레이어와 리소스파일 연동동
         }
    }


    fun setPlayerStatus(song : Song) {
        if (song.isPlaying) {
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        } else {
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
        }
    }


    inner class Timer(private val playTime: Int, var isPlaying: Boolean) : Thread(){

        override fun run() {
            try {
                while (true) {

                    if (song.second >= playTime) {
                        break
                    }
                    if (isPlaying){
                        sleep(1000)
                        song.second++

                        runOnUiThread {
                            binding.songProgressSb.progress = song.second* 1000 / playTime
                            binding.songTimeStartTv.text =
                                String.format("%02d:%02d",song.second/60,song.second%60)
                        }

                    }
                }

            }catch (e : InterruptedException){
                Log.d("interrupt", "스레드가 종료")
            }


            }
        }

    override fun onDestroy(){
        timer.interrupt()
        super.onDestroy()
    }



}






