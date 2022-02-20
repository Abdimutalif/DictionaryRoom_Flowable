package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.uz.dictionaryroom_flowable.R
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.PagerStateAdapter3
import com.uz.dictionaryroom_flowable.databinding.FragmentEditBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentEditBinding
    private lateinit var pagerStateAdapter3: PagerStateAdapter3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        pagerStateAdapter3 = PagerStateAdapter3(this)
        binding.viewpager2.adapter = pagerStateAdapter3


        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.bottomView.selectedItemId = R.id.category

                } else {
                    binding.bottomView.selectedItemId = R.id.words

                }
            }
        })
        binding.bottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.category -> binding.viewpager2.currentItem = 0

                R.id.words -> binding.viewpager2.currentItem = 1
            }
            true
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}