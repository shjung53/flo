package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSaveAlbumBinding

class SaveAlbumRVAdapter() : RecyclerView.Adapter<SaveAlbumRVAdapter.ViewHolder>(){

    private val albums = ArrayList<Album>()

    interface MyItemClickListener{
        fun onRemoveSave(albumId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveAlbumRVAdapter.ViewHolder {
        val binding: ItemSaveAlbumBinding = ItemSaveAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.binding.saveAlbumBtnMoreIv.setOnClickListener {
            removeItem(position)
            mItemClickListener.onRemoveSave(albums[position].id)
        }
    }

    override fun getItemCount(): Int = albums.size

    fun addAlbums(albums: ArrayList<Album>){
        this.albums.clear()
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albums.removeAt(position)
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