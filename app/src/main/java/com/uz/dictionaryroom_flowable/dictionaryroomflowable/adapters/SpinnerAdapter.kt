package com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.uz.dictionaryroom_flowable.databinding.ItemDropBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category

class SpinnerAdapter(val list: ArrayList<Category>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Category = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemDropBinding: ItemDropBinding = if (convertView == null) {
            ItemDropBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            ItemDropBinding.bind(convertView)
        }
        itemDropBinding.apply {
            text.text = list[position].name
        }
        return itemDropBinding.root
    }
}
