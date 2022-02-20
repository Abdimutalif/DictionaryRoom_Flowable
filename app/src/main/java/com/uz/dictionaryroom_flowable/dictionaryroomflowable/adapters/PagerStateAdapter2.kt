package com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.CategoryListFragment
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category

class PagerStateAdapter2(var fragment: Fragment,var list:List<Category>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =list.size

    override fun createFragment(position: Int): Fragment {
        return CategoryListFragment.newInstance(list[position])
    }

}