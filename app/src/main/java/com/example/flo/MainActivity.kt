package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var miniplayer: MiniPlayer

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

        val song = Song("라일락", "아이유 (IU)", 215, false)

        setMiniPlayerStatus(!song.isPlaying)

        miniplayer = MiniPlayer(song.isPlaying)

        miniplayer.start()


        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            startActivity(intent)
        }






        Log.d("Log test", song.title + song.singer)





        binding.mainMiniplayerBtn.setOnClickListener {
            setMiniPlayerStatus(false)
            song.isPlaying = true
        }
        binding.mainPauseBtn.setOnClickListener {
            setMiniPlayerStatus(true)
            song.isPlaying = false
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

    fun setMiniPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        } else {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        }
    }


    inner class MiniPlayer(var isPlaying: Boolean) : Thread() {
        private var second = 0
            override fun run() {
                try{
                    while (true) {
                        if (second >= 10) {
                            break
                        }
                        if (isPlaying) {
                            sleep(1000)
                            second++
                            println("1초행")
                            runOnUiThread{
                                binding.mainMiniPlayerSb.progress = second * 100
                            }
                        }
                    }
        }catch (e : InterruptedException){
                    Log.d("interrupt", "스레드가 종료")
                }
            }
    }
    override fun onDestroy(){
        miniplayer.interrupt()
        super.onDestroy()
    }
}





