package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSaveBinding

class SaveRVAdapter(private val saveList : ArrayList<Save>) : RecyclerView.Adapter<SaveRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveRVAdapter.ViewHolder {

        val binding : ItemSaveBinding = ItemSaveBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveRVAdapter.ViewHolder, position: Int) {
        holder.bind(saveList[position])

    }

    override fun getItemCount(): Int = saveList.size

    inner class ViewHolder(val binding : ItemSaveBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(save : Save){

            binding.saveSongCoverIv.setImageResource(save.coverImg!!)
            binding.saveSongTitleTv.text = save.title
            binding.saveSongSingerTv.text = save.singer

        }
    }


}