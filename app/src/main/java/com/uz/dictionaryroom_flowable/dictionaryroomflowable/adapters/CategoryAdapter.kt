package com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uz.dictionaryroom_flowable.R
import com.uz.dictionaryroom_flowable.databinding.ItemCategoryBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import java.util.*

class CategoryAdapter(var context: Context,var listener:OnItemClick) : ListAdapter<Category, CategoryAdapter.Vh>(MyDiffUtil()) {
    class MyDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    inner class Vh(private var itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {

        fun onBind(category: Category,position: Int) {
            itemCategoryBinding.apply {
                name.text = category.name
                popBtn.setOnClickListener {
                    val popupMenu = PopupMenu(context, popBtn)
                    popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.edit -> listener.onItemClickEdit(category,position)
                            R.id.delete -> listener.onItemClickDelete(category,position)
                        }
                        true
                    }
                    popupMenu.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    interface OnItemClick {
        fun onItemClickEdit(category: Category, position: Int)
        fun onItemClickDelete(category: Category, position: Int)

    }

}