package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.fragment.HomeFragment
import com.example.flo.fragment.LockerFragment
import com.example.flo.fragment.LookFragment
import com.example.flo.fragment.SearchFragment
import java.util.*




class MainActivity : AppCompatActivity() {

    private var mediaPlayer : MediaPlayer? = null

    lateinit var binding: ActivityMainBinding

    lateinit var timer: Timer

    private var songs = ArrayList<Song>()

    private  var nowPos = 0

    private lateinit var songDB :SongDatabase






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
        inputDummySongs()
        inputDummyAlbums()
        initPlayList()
        initSong()
        initClickListener()



        binding.mainPlayerLayout.setOnClickListener {

            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", songs[nowPos].id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
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

    private fun initClickListener() {

        binding.mainMiniplayerBtn.setOnClickListener {
            setMiniStatus(true)
            mediaPlayer?.start()
        }

        binding.mainPauseBtn.setOnClickListener {
            setMiniStatus(false)
            mediaPlayer?.pause()
        }

        binding.mainBtnPreviousBtn.setOnClickListener {
            moveSong(-1)
            if (songs[nowPos + 1].isPlaying) {
                setMiniStatus(true)
                mediaPlayer?.start()
            }
        }

            binding.mainBtnNextBtn.setOnClickListener {
                moveSong(+1)
                if (songs[nowPos - 1].isPlaying) {
                    setMiniStatus(true)
                    mediaPlayer?.start()
                }
            }
        }

    private fun moveSong(direct: Int) {

        if(nowPos + direct < 0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){
            Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct

        timer.interrupt()

        mediaPlayer?.release()
        mediaPlayer = null

        setMiniPlayer(songs[nowPos])
        startTimer()

    }

    private fun setMiniStatus(isPlaying: Boolean) {

        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        } else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }

    private fun setMiniPlayer(song : Song) {

        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        binding.mainMiniPlayerTitleTv.text = song.title
        binding.mainMiniPlayerSingerTv.text = song.singer
        binding.mainMiniPlayerSb.max = song.playTime * 1000
        binding.mainMiniPlayerSb.progress = song.second

        setMiniStatus(song.isPlaying)

        mediaPlayer = MediaPlayer.create(this, music)
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].isPlaying)
        timer.start()
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.SongDao().getSongs())
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        startTimer()
        setMiniPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId : Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }



    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.SongDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.SongDao().insert(Song("라일락", "아이유 (IU)",0, 214, false,"music_lilac", R.drawable.img_album_exp2, false))
        songDB.SongDao().insert(Song("Butter","방탄소년단(BTS)",0,165,false,"music_butter", R.drawable.img_album_exp, false))
        songDB.SongDao().insert(Song("Champagne Poetry","Drake",0,337,false,"music_champagnepoetry", R.drawable.img_clb, false))
        songDB.SongDao().insert(Song("Stay","The Kid LAROI, Justin Bieber",0,138,false,"music_stay", R.drawable.img_stay, false))
        songDB.SongDao().insert(Song("call on me","Josef Salvat",0,180,false,"music_callonme", R.drawable.img_callonme, false))
        songDB.SongDao().insert(Song("Take Care (Deluxe)","Drake",0,248,false,"music_takecare", R.drawable.img_takecare, false))
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.AlbumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.AlbumDao().insert(Album(1,"IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2))

        songDB.AlbumDao().insert(Album(2,"Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))

        songDB.AlbumDao().insert(Album(3,"Certified Lover Boy","Drake", R.drawable.img_clb))

        songDB.AlbumDao().insert(Album(4,"Stay","The Kid LAROI, Justin Bieber", R.drawable.img_stay))

        songDB.AlbumDao().insert(Album(5,"call on me","Josef Salvat", R.drawable.img_callonme))

        songDB.AlbumDao().insert(Album(6,"Take Care (Deluxe)","Drake", R.drawable.img_takecare))

    }




    inner class Timer(var isPlaying: Boolean) : Thread(){
        private  var second = songs[nowPos].second
        private var playTime = songs[nowPos].playTime * 1000


        override fun run() {

            try {

                while (true) {

                    if(second >= playTime)

                        break


                    if (isPlaying){
                        sleep(1)

                        runOnUiThread {
                            binding.mainMiniPlayerSb.progress = mediaPlayer?.currentPosition!!
                        }
                    }
                }

            }catch (e : InterruptedException){
                Log.d("interrupt", "스레드가 종료")
            }


        }
    }










// 시작할 때
    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        songs[nowPos] = if(songId == 0){
            songDB.SongDao().getSong(1)
        }else{
            songDB.SongDao().getSong(songId)
        }

        Log.d("song ID", songs[nowPos].id.toString())
        setMiniPlayer(songs[nowPos])
        startTimer()
        if(songs[nowPos].isPlaying){
            mediaPlayer?.seekTo(songs[nowPos].second)
            mediaPlayer?.start()
        }
        Log.d("메인온스타트","메인온스타트")
    }

    override fun onPause() {
        super.onPause()
        timer.interrupt()
        mediaPlayer?.pause()
        Log.d("메인온퍼즈","메인온퍼즈")
    }



    override fun onDestroy(){
        super.onDestroy()
        mediaPlayer?.release() // mediaPlayer 리소스 해제
        mediaPlayer = null
        Log.d("메인온디스트로이","메인온디스트로이")
    }
}






