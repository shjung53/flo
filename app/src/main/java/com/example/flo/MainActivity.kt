package com.example.flo

import android.content.Intent
import android.content.res.Resources
import android.content.res.loader.ResourcesLoader
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.fragment.HomeFragment
import com.example.flo.fragment.LockerFragment
import com.example.flo.fragment.LookFragment
import com.example.flo.fragment.SearchFragment
import com.google.gson.Gson
import java.util.*

var mediaPlayer : MediaPlayer? = null

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var gson : Gson = Gson()

    private var song : Song = Song()

    private var handler = Handler(Looper.getMainLooper())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

        val song = Song("라일락", "아이유 (IU)",0, 214, false,"music_lilac")

        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        mediaPlayer = MediaPlayer.create(this, music)

        setMiniPlayer(song)


        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }


        binding.mainMiniPlayerSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })






        Log.d("Log test", song.title + song.singer)





        binding.mainMiniplayerBtn.setOnClickListener {
            song.isPlaying = true
            setMiniPlayerStatus(true)
            mediaPlayer?.start()
        }
        binding.mainPauseBtn.setOnClickListener {
            song.isPlaying = false
            setMiniPlayerStatus(false)
            mediaPlayer?.pause()
        }




        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }

    private fun setMiniPlayer(song : Song) {
        binding.mainMiniPlayerTitleTv.text = song.title
        binding.mainMiniPlayerSingerTv.text = song.singer
        binding.mainMiniPlayerSb.max = mediaPlayer!!.duration
        binding.mainMiniPlayerSb.progress = song.second
    }

    private fun setMiniPlayerStatus(isPlaying : Boolean) {

        if (isPlaying) {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        } else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }
    }

    private fun Timer(){
        handler.post{
            binding.mainMiniPlayerSb.progress = mediaPlayer?.currentPosition!!
        }
    }










// 시작할 때
    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song", null)

//        처음 앱 실행시
        song = if(jsonSong == null){
            Song("라일락", "아이유 (IU)",0, 214, false,"music_lilac")
        }else{
            gson.fromJson(jsonSong, Song::class.java) // json을 song 데이터 객체로 변환
        }
        setMiniPlayer(song)
    }
}





