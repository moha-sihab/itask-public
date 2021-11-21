package com.sihabudin.itask.addCategory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.sihabudin.itask.R
import com.sihabudin.itask.addCategory.viewModel.AddCategoryViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.utils.Converter
import com.sihabudin.itask.core.utils.CustomToast
import com.sihabudin.itask.databinding.FragmentAddCategoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddCategoryFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!
    private val addCategoryViewModel: AddCategoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddCategory.setOnClickListener {
            insertCategory()


        }
        binding.btnCloseCategory.setOnClickListener {
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertCategory() {
        val category = CategoryModel(
            id_category = 0,
            category = binding.txtCategory.text.toString(),
            isActive = true,
            colorLabel = getColorLabel(),
            createDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!,
            createBy = "system",
            updateDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!,
            updateBy = "system"
        )
        if (validationInput(category)) {
            addCategoryViewModel.insertCategory(category)
            dismiss()
        }
    }

    private fun validationInput(category: CategoryModel): Boolean {
        var valid = true
        var message = ""

        if (category.category == "" || category.colorLabel == "") {
            message = getString(R.string.messageValidInputCategory)
            valid = false
            CustomToast().errorSaveToast(requireContext(), message)
        }



        return valid
    }

    private fun getColorLabel(): String {
        var label = ""

        val chipGroup = binding.chipColor
        val totalChip: Int = chipGroup.childCount

        var countChip = 0

        while (countChip < totalChip) {
            val color: Chip = chipGroup.getChildAt(countChip) as Chip
            if (color.isChecked) {
                label = color.text.toString()
            }
            countChip++
        }
        return label


    }
}