package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.uz.dictionaryroom_flowable.R
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.WordAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.database.AppDatabase
import com.uz.dictionaryroom_flowable.databinding.FragmentAddWordBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddWordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddWordFragment : Fragment() {
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

    lateinit var binding: FragmentAddWordBinding
    lateinit var wordAdapter: WordAdapter
    lateinit var appDatabase: AppDatabase

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWordBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        wordAdapter = WordAdapter(requireContext(), object : WordAdapter.OnItemClick {
            override fun onItemEdit(word: Word, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("word", word)
                findNavController().navigate(R.id.editWordsFragment, bundle)
            }

            override fun onItemDelete(word: Word, position: Int) {
                appDatabase.wordDao().deleteWord(word)
            }

        })

        binding.rv.adapter = wordAdapter
        appDatabase.wordDao().getAllWord().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                wordAdapter.submitList(it)
            }, {
            }) {
            }
        binding.plusWordBtn.setOnClickListener {
            val bundle = Bundle()
            findNavController().navigate(R.id.addWordsFragment,bundle)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
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
         * @return A new instance of fragment AddWordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddWordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}