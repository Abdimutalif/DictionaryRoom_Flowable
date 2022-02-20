package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.CategoryAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.database.AppDatabase
import com.uz.dictionaryroom_flowable.databinding.FragmentAddCategoryBinding
import com.uz.dictionaryroom_flowable.databinding.ItemAddCategoryBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddCategoryFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentAddCategoryBinding
    lateinit var appDatabase: AppDatabase
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        categoryAdapter = CategoryAdapter(requireContext(), object : CategoryAdapter.OnItemClick {
            @SuppressLint("CheckResult")
            override fun onItemClickEdit(category: Category, position: Int) {
                val builder1 = AlertDialog.Builder(requireContext()).create()
                val itemBinding1 = ItemAddCategoryBinding.inflate(inflater)
                builder1.setView(itemBinding1.root)
                itemBinding1.edCategory.setText(category.name)
                itemBinding1.cancelBtn.setOnClickListener {
                    builder1.dismiss()
                }
                itemBinding1.saveBtn.setOnClickListener {
                    val name = itemBinding1.edCategory.text.toString()
                    if (name.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Ismingizni kiriting!!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        category.name=name
                        appDatabase.categoryDao().editCategory(category)
                        builder1.dismiss()
                    }
                }
                builder1.show()
                appDatabase.categoryDao()
                    .getAllCategory()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        categoryAdapter.submitList(it)
                    }, {
                    }) {
                    }
            }


            override fun onItemClickDelete(category: Category, position: Int) {
                appDatabase.categoryDao().deleteCategory(category)
            }

        })
        binding.apply {
            plusCategoryBtn.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext()).create()
                val itemBinding = ItemAddCategoryBinding.inflate(inflater)
                builder.setView(itemBinding.root)
                itemBinding.cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                itemBinding.saveBtn.setOnClickListener {
                    val name = itemBinding.edCategory.text.toString()
                    if (name.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Ismingizni kiriting!!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        appDatabase.categoryDao().addCategory(Category(name = name))
                        builder.dismiss()
                    }
                }

                builder.show()

            }
            binding.rv.adapter = categoryAdapter

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            appDatabase.categoryDao()
                .getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    categoryAdapter.submitList(it)
                }, {
                }) {
                }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}