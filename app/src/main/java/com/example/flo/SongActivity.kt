package com.example.flo
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat

class SongActivity : AppCompatActivity() {

    private var mediaPlayer : MediaPlayer? = null

    private lateinit var player: Player

    private var song: Song = Song()


    private  var gson : Gson = Gson()

    private var timeFormat = SimpleDateFormat("mm:ss") // 시간 표현



    lateinit var binding: ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        player = Player(song.isPlaying)
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
            onBackPressed()
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
                binding.songTimeStartTv.text = timeFormat.format(mediaPlayer?.currentPosition)
                if(fromUser)  //만약 유저가 seekBar를 움직이면
                    mediaPlayer?.seekTo(progress) // 위치 바꾼 곳에서 재생
           }

//            사용자가 터치할때
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.songTimeStartTv.text = timeFormat.format(mediaPlayer?.currentPosition)
            }
//            사용자가 터치 끝났을 때
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.songTimeStartTv.text = timeFormat.format(mediaPlayer?.currentPosition)
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
            binding.songProgressSb.max = mediaPlayer?.duration!! // seekBar max mediaPlayer와 연동
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



    inner class Player(var isPlaying: Boolean) : Thread(){
        private var duration = mediaPlayer?.duration


        override fun run() {

            try {

                while (true) {

                    if(song.second >= duration!!)

                        break


                    if (isPlaying){
                        sleep(1)

                        runOnUiThread {
                            binding.songProgressSb.progress = mediaPlayer?.currentPosition!!
                            binding.songTimeStartTv.text =
                                timeFormat.format(mediaPlayer?.currentPosition!!)
                            song.second = mediaPlayer?.currentPosition!!
                        }
                    }
                }

            }catch (e : InterruptedException){
                Log.d("interrupt", "스레드가 종료")
            }


            }
        }




    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song", null)
        song = gson.fromJson(jsonSong, Song::class.java)
        mediaPlayer?.seekTo(song.second)
        binding.songProgressSb.progress = song.second
        setPlayerStatus(song.isPlaying)
        if(song.isPlaying){
            mediaPlayer?.start()
        }
        Log.d("송온스타트","송온스타트")
    }


    override fun onPause() {
        super.onPause()
        player.isPlaying = false // 스레드 중지
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) // 간단한 데이터 기기에 저장 ex.비밀번호
        val editor = sharedPreferences.edit() //sharedPreferences 조작
        val json = gson.toJson(song) // song 데이터 객체를 json으로 변환
        editor.putString("song", json)
        editor.apply() // sharedPreferences에 적용
        mediaPlayer?.pause()
        Log.d("송온퍼즈","송온퍼즈")
    }

    override fun onStop() {
        super.onStop()
        Log.d("송온스탑","송온스탑")
    }

    override fun onDestroy(){
        super.onDestroy()
        player.interrupt()
        mediaPlayer?.release() // mediaPlayer 리소스 해제
        mediaPlayer = null
    }

}






