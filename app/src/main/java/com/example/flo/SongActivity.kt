package com.example.flo
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import java.text.SimpleDateFormat

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    private var mediaPlayer : MediaPlayer? = null

    private lateinit var timer: Timer

    private var timeFormat = SimpleDateFormat("mm:ss") // 시간 표현

    private var songs = ArrayList<Song>()
    private  var nowPos = 0
    private lateinit var songDB :SongDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initPlayList()
        initSong()
        if(songs[nowPos].isPlaying){
            mediaPlayer?.seekTo(songs[nowPos].second)
            mediaPlayer?.start()}
        initClickListener()
        playType()
        initSeekBarCL()

    }

    private fun initSeekBarCL() {
        binding.songProgressSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            //            사용자가 터치중일때
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)  //만약 유저가 seekBar를 움직이면
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

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.SongDao().getSongs())
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        startTimer()
        setPlayer(songs[nowPos])
    }




    private fun getPlayingSongPosition(songId : Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }


    private fun initClickListener(){

        binding.songBtnDownIb.setOnClickListener {
            onBackPressed()
        }

        binding.songBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songBtnPreviousIv.setOnClickListener{
            if(songs[nowPos].isPlaying){
                moveSong(-1)
                mediaPlayer?.start()
            }else{
                moveSong(-1)
            }
        }

        binding.songBtnNextIv.setOnClickListener{
            if(songs[nowPos].isPlaying){
                moveSong(+1)
                mediaPlayer?.start()
            }else{
                moveSong(+1)
            }

        }
        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun moveSong(direct: Int){
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

        setPlayer(songs[nowPos])
        startTimer()
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.SongDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)}
            else{
                binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
            }
        }


    private fun startTimer() {
        timer = Timer(songs[nowPos].isPlaying)
        timer.start()
    }

    private fun playType(){
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
    }


    private fun setPlayer(song: Song){

        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        binding.songUpperTitleTv.text = song.title
        binding.songUpperSingerTv.text = song.singer
        binding.songTimeStartTv.text = timeFormat.format(song.second)
        binding.songTimeEndTv.text = timeFormat.format(song.playTime * 1000)
        binding.songAlbumCoverIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.max = song.playTime * 1000
        binding.songProgressSb.progress = song.second

        setPlayerStatus(song.isPlaying)

        if (song.isLike) {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        mediaPlayer = MediaPlayer.create(this, music)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {

        timer.isPlaying = isPlaying


        if (isPlaying) {
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
            mediaPlayer?.pause()
        }
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
                            binding.songProgressSb.progress = mediaPlayer?.currentPosition!!
                            binding.songTimeStartTv.text =
                                timeFormat.format(mediaPlayer?.currentPosition!!)
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

        val spf = getSharedPreferences("song", MODE_PRIVATE) // 간단한 데이터 기기에 저장 ex.비밀번호
        val editor = spf.edit() //sharedPreferences 조작

        editor.putInt("songsId", songs[nowPos].id)
        editor.apply() // sharedPreferences에 적용
        mediaPlayer?.pause()
        Log.d("송온퍼즈","송온퍼즈")
    }


    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // mediaPlayer 리소스 해제
        mediaPlayer = null
        Log.d("송온디스트로이","송온디스트로이")
    }

}






