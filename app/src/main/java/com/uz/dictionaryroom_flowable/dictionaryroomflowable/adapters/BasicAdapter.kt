package com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uz.dictionaryroom_flowable.databinding.ItemBasicBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word

class BasicAdapter(var list: List<Word>,var listener:OnClickItem): RecyclerView.Adapter<BasicAdapter.MyViewHolder>() {

    inner class MyViewHolder(var itemBasicBinding: ItemBasicBinding) :
        RecyclerView.ViewHolder(itemBasicBinding.root) {
        fun onBind(word: Word, position: Int) {
            itemBasicBinding.apply {
                des.text = word.translate
                name.text = word.word
                forwardBtn.setOnClickListener {
                    listener.onItemClick(word, position)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemBasicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size
    interface OnClickItem {
        fun onItemClick(word: Word, position: Int)
    }
}