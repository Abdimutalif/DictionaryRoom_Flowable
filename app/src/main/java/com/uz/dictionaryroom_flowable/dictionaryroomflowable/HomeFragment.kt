package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.PagerStateAdapter
import com.uz.dictionaryroom_flowable.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uz.dictionaryroom_flowable.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentHomeBinding
    lateinit var pagerStateAdapter: PagerStateAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        pagerStateAdapter = PagerStateAdapter(this)
        binding.viewpager.adapter = pagerStateAdapter
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.bottomView.selectedItemId = R.id.home
                } else {
                    binding.bottomView.selectedItemId = R.id.liked
                }
            }
        })
        binding.bottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> binding.viewpager.currentItem = 0
                R.id.liked -> binding.viewpager.currentItem = 1
            }
            true
        }

        binding.editBtn.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }
        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}