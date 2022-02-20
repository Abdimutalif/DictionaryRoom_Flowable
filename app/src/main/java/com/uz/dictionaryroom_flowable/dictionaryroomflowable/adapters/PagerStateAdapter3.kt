package com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.AddCategoryFragment
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.AddWordFragment

class PagerStateAdapter3(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            AddCategoryFragment()
        } else {
            AddWordFragment()
        }

    }

}