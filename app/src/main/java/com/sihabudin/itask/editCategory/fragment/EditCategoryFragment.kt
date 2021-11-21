package com.sihabudin.itask.editCategory.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.utils.Converter
import com.sihabudin.itask.core.utils.CustomToast
import com.sihabudin.itask.databinding.FragmentEditCategoryBinding
import com.sihabudin.itask.editCategory.viewModel.EditCategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EditCategoryFragment : Fragment() {

    private val args by navArgs<EditCategoryFragmentArgs>()
    private var _binding: FragmentEditCategoryBinding? = null
    private val binding get() = _binding!!
    private val editCategoryViewModel: EditCategoryViewModel by viewModel()
    private lateinit var categoryModel: CategoryModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingData()

        binding.btnAddCategory.setOnClickListener {
            updateCategory()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().popBackStack()
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bindingData() {
        editCategoryViewModel.getCategory(args.idCategory)
            .observe(viewLifecycleOwner, { category ->
                if (category != null) {
                    categoryModel = category[0]
                    binding.txtCategory.setText(category[0].category)
                    binding.switchActiveCategory.isChecked = category[0].isActive
                    when (category[0].colorLabel) {
                        "Red" -> binding.chipColor.check(R.id.chipColorRed)
                        "Blue" -> binding.chipColor.check(R.id.chipColorBlue)
                        "Yellow" -> binding.chipColor.check(R.id.chipColorYellow)
                        "Green" -> binding.chipColor.check(R.id.chipColorGreen)
                        "Purple" -> binding.chipColor.check(R.id.chipColorPurple)
                    }
                }

            })
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

    private fun updateCategory() {
        val category = CategoryModel(
            id_category = args.idCategory,
            category = binding.txtCategory.text.toString(),
            isActive = binding.switchActiveCategory.isChecked,
            colorLabel = getColorLabel(),
            createBy = categoryModel.createBy,
            createDate = categoryModel.createDate,
            updateBy = "system",
            updateDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!

        )
        if (validationInput(category)) {
            editCategoryViewModel.updateCategory(category)
            CustomToast().successSaveToast(requireContext())
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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false
    }
}