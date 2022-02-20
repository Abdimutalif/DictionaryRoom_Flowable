package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uz.dictionaryroom_flowable.databinding.FragmentAboutBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "word"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var word: Word
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            word = it.getSerializable("word") as Word
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.image.setImageURI(Uri.parse(word.photoUri))
        binding.tvName.text = word.word
        binding.des.text = word.translate
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param word Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(word: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, word)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}