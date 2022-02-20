package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uz.dictionaryroom_flowable.R
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.database.AppDatabase
import com.uz.dictionaryroom_flowable.databinding.FragmentCategoryListBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.BasicAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "category"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var category: Category
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getSerializable(ARG_PARAM1) as Category
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var binding: FragmentCategoryListBinding
    lateinit var appDatabase: AppDatabase
    lateinit var basicAdapter: BasicAdapter
    lateinit var list:ArrayList<Word>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        list = ArrayList(appDatabase.wordDao().getAllWordByCategoryId(category.id))
        basicAdapter= BasicAdapter(list, object : BasicAdapter.OnClickItem {
            override fun onItemClick(word: Word, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("word",word)
                findNavController().navigate(R.id.aboutFragment,bundle)
            }

        })

        binding.rv.adapter=basicAdapter


        return binding.root
    }

    companion object {
        fun newInstance(category: Category) =
            CategoryListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, category)
                }
            }
    }
}