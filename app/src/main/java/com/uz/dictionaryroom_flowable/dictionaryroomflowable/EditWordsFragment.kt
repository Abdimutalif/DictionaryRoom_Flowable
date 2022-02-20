package com.uz.dictionaryroom_flowable.dictionaryroomflowable

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.karumi.dexter.BuildConfig
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.adapters.SpinnerAdapter
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.database.AppDatabase
import com.uz.dictionaryroom_flowable.databinding.FragmentEditWordsBinding
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "word"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditWordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditWordsFragment : Fragment() {
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

    private lateinit var binding: FragmentEditWordsBinding
    private var fileAbsolutePath = ""
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var list: ArrayList<Category>
    private lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditWordsBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        list = ArrayList(appDatabase.categoryDao().getAllCategoryList())
        fileAbsolutePath = word.photoUri

        spinnerAdapter = SpinnerAdapter(list)
        binding.edCategory.adapter = spinnerAdapter
        binding.apply {
            image.setImageURI(Uri.parse(word.photoUri))
            edWord.setText(word.word)
            edTranslate.setText(word.translate)

//            edCategory.setSelection(word.categoryId)
            edCategory.setSelection(word.categoryId-1)
        }
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            image.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Tanlang!!!")
                builder.setMessage("Cameradan rasm olasizmi yoki Galeriyadanmi rasm olasizmi ?")
                builder.setPositiveButton("Cameradan")
                { dialog, which ->
                    onResultCamera()
                }
                builder.setNegativeButton("Galeriyadan") { dialog, which -> onResultGallery() }
                builder.show()
            }
            saveBtn.setOnClickListener {
                if (isValidation()) {
                    appDatabase.wordDao().editWord(word)
                    Navigation.findNavController(it).popBackStack()
                }
            }
        }
        return binding.root
    }

    private fun isValidation(): Boolean {
        val word1 = binding.edWord.text.toString()
        val translate = binding.edTranslate.text.toString()
        val category1 = list[binding.edCategory.selectedItemPosition].name
        val id = list[binding.edCategory.selectedItemPosition].id

        when {
            word1.isEmpty() -> {
                Toast.makeText(requireContext(), "So'z kiritilmagan", Toast.LENGTH_SHORT).show()
                return false
            }
            translate.isEmpty() -> {
                Toast.makeText(requireContext(), "Tarjimasi kiritilmagan", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            fileAbsolutePath.isEmpty() -> {
                Toast.makeText(requireContext(), "Rasm tanlanmagan", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                word.word = word1
                word.translate = translate
                word.photoUri = fileAbsolutePath
                word.categoryName = category1
                word.categoryId = id
                return true
            }
        }
    }

    private fun onResultGallery() {
        Dexter.withContext(requireContext())
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    getImageContent.launch("image/*")
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri =
                            Uri.fromParts("package", requireActivity().packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } else {
                        response.requestedPermission
                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Permission!!!")
                    builder.setMessage("Ruxsat berishni surayabdi!!!")
                    builder.setPositiveButton("Ruxsat surash")
                    { dialog, which ->
                        p1?.continuePermissionRequest()
                    }
                    builder.setNegativeButton("Ruxsat suramaslik!!!") { dialog, which -> p1?.cancelPermissionRequest() }
                    builder.show()

                }

            }).check()
    }

    private fun onResultCamera() {
        Dexter.withContext(requireContext())
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    val photoFile = try {
                        createImageFile()
                    } catch (e: Exception) {
                        null
                    }
                    photoFile?.also {
                        val uri = FileProvider.getUriForFile(
                            requireContext(),
                            BuildConfig.APPLICATION_ID,
                            it
                        )
                        getCameraImage.launch(uri)
                    }

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri =
                            Uri.fromParts("package", requireActivity().packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } else {
                        response.requestedPermission
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Permission!!!")
                    builder.setMessage("Ruxsat berishni surayabdi!!!")
                    builder.setPositiveButton("Ruxsat surash")
                    { dialog, which ->
                        p1?.continuePermissionRequest()
                    }
                    builder.setNegativeButton("Ruxsat suramaslik!!!") { dialog, which -> p1?.cancelPermissionRequest() }
                    builder.show()

                }

            }).check()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val m = System.currentTimeMillis()
        val externalFilesDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("G21_$m", ".jpg", externalFilesDir)
            .apply {
                fileAbsolutePath = absolutePath
            }
    }

    private val getCameraImage = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            binding.image.setImageURI(Uri.parse(fileAbsolutePath))
        }
    }
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@registerForActivityResult
            binding.image.setImageURI(uri)
            val openInputStream = requireActivity().contentResolver?.openInputStream(uri)
            val m = System.currentTimeMillis()
            val file = File(requireActivity().filesDir, "$m.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()
            fileOutputStream.close()
            fileAbsolutePath = file.absolutePath
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param word Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditWordsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(word: String, param2: String) =
            EditWordsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, word)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}