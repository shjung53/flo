package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSaveBinding

class SaveRVAdapter() : RecyclerView.Adapter<SaveRVAdapter.ViewHolder>() {

    private val songs = ArrayList<Song>()

    interface MyItemClickListener{
        fun onRemoveSave(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener



    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveRVAdapter.ViewHolder {

        val binding : ItemSaveBinding = ItemSaveBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.saveBtnMoreIv.setOnClickListener {
            removeItem(position)
            mItemClickListener.onRemoveSave(songs[position].id)
        }
    }

    override fun getItemCount(): Int = songs.size

    fun removeItem(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    fun addSongs(songs: ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }






    inner class ViewHolder(val binding : ItemSaveBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song : Song){
            binding.saveSongCoverIv.setImageResource(song.coverImg!!)
            binding.saveSongTitleTv.text = song.title
            binding.saveSongSingerTv.text = song.singer

        }
    }
}

