package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSaveAlbumBinding

class SaveAlbumRVAdapter() : RecyclerView.Adapter<SaveAlbumRVAdapter.ViewHolder>(){

    private val albums = ArrayList<Album>()


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveAlbumRVAdapter.ViewHolder {
        val binding: ItemSaveAlbumBinding = ItemSaveAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.binding.saveAlbumBtnMoreIv.setOnClickListener {
        }
    }

    override fun getItemCount(): Int =albums.size

    fun addAlbums(albums: ArrayList<Album>){
        this.albums.clear()
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }







    inner class ViewHolder(val binding: ItemSaveAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.saveAlbumCoverIv.setImageResource(album.coverImg!!)
            binding.saveAlbumTitleTv.text = album.title
            binding.saveAlbumSingerTv.text = album.singer
        }
    }

}