package com.sihabudin.itask.category.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.addCategory.fragment.AddCategoryFragment
import com.sihabudin.itask.category.adapter.CategoryListSelectedAdapter
import com.sihabudin.itask.category.viewModel.CategoryViewModel
import com.sihabudin.itask.databinding.FragmentCategoryListSelectedBinding
import com.sihabudin.itask.sharedViewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryListSelectedFragment : DialogFragment() {

    private val categoryViewModel: CategoryViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentCategoryListSelectedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryListSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingData()

        binding.btnAddCategoryCls.setOnClickListener {
            val addCategory = AddCategoryFragment()
            addCategory.show(childFragmentManager,"ADD CATEGORY")
        }
        binding.imageCloseCls.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingData() {
        val categoryListSelectedAdapter = CategoryListSelectedAdapter()

        categoryViewModel.getCategory.observe(viewLifecycleOwner, { categories ->
            if (categories != null) {
                categoryListSelectedAdapter.setData(categories)
            }
        })

        with(binding.rvCategoryListSelected) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = categoryListSelectedAdapter


        }

        categoryListSelectedAdapter.onItemClick = { selectedData ->
            sharedViewModel.setCategorySelected(selectedData)
            dismiss()


        }
    }


}

