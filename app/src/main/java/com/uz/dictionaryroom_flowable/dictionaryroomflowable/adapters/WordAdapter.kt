package com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uz.dictionaryroom_flowable.R
import com.uz.dictionaryroom_flowable.databinding.ItemListWordsBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word

class WordAdapter(var context: Context, var listener: OnItemClick) :
    ListAdapter<Word, WordAdapter.MyHolder>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }

    inner class MyHolder(var itemListWordsBinding: ItemListWordsBinding) :
        RecyclerView.ViewHolder(itemListWordsBinding.root) {
        fun onBind(word: Word, position: Int) {
            itemListWordsBinding.menuBtn.setOnClickListener {
                val popupMenu = PopupMenu(context, itemListWordsBinding.menuBtn)
                popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit -> listener.onItemEdit(word, position)
                        R.id.delete -> listener.onItemDelete(word, position)
                    }
                    true
                }
                popupMenu.show()
            }
            itemListWordsBinding.apply {
                des.text = word.translate
                name.text = word.word
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemListWordsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    interface OnItemClick {
        fun onItemEdit(word: Word, position: Int)
        fun onItemDelete(word: Word, position: Int)
    }
}