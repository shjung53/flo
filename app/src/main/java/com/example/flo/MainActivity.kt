package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.fragment.HomeFragment
import com.example.flo.fragment.LockerFragment
import com.example.flo.fragment.LookFragment
import com.example.flo.fragment.SearchFragment
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private var mediaPlayer : MediaPlayer? = null

    lateinit var binding: ActivityMainBinding

    private var gson : Gson = Gson()

    private var song : Song = Song()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

        val song = Song("라일락", "아이유 (IU)",0, 215, false,"music_lilac")

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






        Log.d("Log test", song.title + song.singer)





        binding.mainMiniplayerBtn.setOnClickListener {
            song.isPlaying = true
            setMiniPlayer(song)
        }
        binding.mainPauseBtn.setOnClickListener {
            song.isPlaying = false
            setMiniPlayer(song)
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

    fun setMiniPlayer(song : Song) {

        binding.mainMiniPlayerTitleTv.text = song.title
        binding.mainMiniPlayerSingerTv.text = song.singer
        binding.mainMiniPlayerSb.progress = song.second * 1000 / song.playTime

        if (song.isPlaying) {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        } else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }
    }


    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song", null)

//        처음 앱 실행시
        song = if(jsonSong == null){
            Song("라일락", "아이유 (IU)",0, 215, false,"music_lilac")
        }else{
            gson.fromJson(jsonSong, Song::class.java) // json을 song 데이터 객체로 변환
        }
        setMiniPlayer(song)
    }
}





