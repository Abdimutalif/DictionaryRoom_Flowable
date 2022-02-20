package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.CategoryAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.PagerStateAdapter2
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.database.AppDatabase
import com.uz.dictionaryroom_flowable.databinding.FragmentBasicBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentBasicBinding
    lateinit var appDatabase: AppDatabase
    lateinit var list: ArrayList<Category>
    lateinit var pagerStateAdapter2: PagerStateAdapter2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        list = ArrayList(appDatabase.categoryDao().getAllCategoryList())
        pagerStateAdapter2 = PagerStateAdapter2(this, list)
        binding.viewpager.adapter = pagerStateAdapter2

        binding.apply {

            TabLayoutMediator(
                binding.tabLayout, binding.viewpager
            ) { tab, position ->
                tab.text = list[position].name
            }.attach()
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
         * @return A new instance of fragment BasicFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}