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
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.adapters.SeekBarBindingAdapter
import androidx.databinding.adapters.SeekBarBindingAdapter.setOnSeekBarChangeListener
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {


    private lateinit var player: Player

    private val song: Song = Song()

    private var mediaPlayer : MediaPlayer? = null

    private  var gson : Gson = Gson()


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
            song.isPlaying = true
            setPlayerStatus(true)
            mediaPlayer?.start()
        }

        binding.songBtnPauseIv.setOnClickListener {
            player.isPlaying = false
            song.isPlaying = false
            setPlayerStatus(false)
            mediaPlayer?.pause()
        }
        binding.songProgressSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

//            사용자가 터치중일때
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.songTimeStartTv.text = String.format("%02d:%02d", progress * song.playTime/1000/60,progress * song.playTime/1000%60)
                song.second = progress * song.playTime / 1000

                if(fromUser)  //만약 유저가 seekBar를 움직이면
                    mediaPlayer?.seekTo(progress) // 위치 바꾼 곳에서 재생

           }

//            사용자가 터치할때
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
//            사용자가 터치 끝났을 때
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        }
        )



    }


    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("second") && intent.hasExtra("playTime") && intent.hasExtra(
                "isPlaying") && intent.hasExtra("music")
        ) {
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.second = intent.getIntExtra("second", 0)
            song.playTime = intent.getIntExtra("playTime", 0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            song.music = intent.getStringExtra("music")!!
            val music = resources.getIdentifier(song.music, "raw", this.packageName) // 리소스를 반환(리소스이름, 폴더, 패키지이름)

            binding.songTimeEndTv.text = String.format("%02d:%02d", song.playTime/60,song.playTime%60)
            binding.songUpperTitleTv.text = song.title
            binding.songUpperSingerTv.text = song.singer
            setPlayerStatus(song.isPlaying)
            mediaPlayer = MediaPlayer.create(this, music) // mediaPlayer 와 Music 연동
        }
    }


    fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        } else {
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
        }
    }



    inner class Player(private val playTime: Int, var isPlaying: Boolean) : Thread(){

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


    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        player.isPlaying = false // 스레드 중지
        song.isPlaying = false
        song.second = (binding.songProgressSb.progress * song.playTime) / 1000
        setPlayerStatus(false) // 일시정지, 플레이버튼 보여주기

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) // 간단한 데이터 기기에 저장 ex.비밀번호
        val editor = sharedPreferences.edit() //sharedPreferences 조작
        val json = gson.toJson(song) // song 데이터 객체를 json으로 변환
        editor.putString("song", json)
        editor.apply() // sharedPreferences에 적용
    }
    override fun onDestroy(){
        super.onDestroy()
        player.interrupt()
        mediaPlayer?.release() // mediaPlayer 리소스 해제
        mediaPlayer = null
    }



}






