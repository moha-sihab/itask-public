package com.sihabudin.itask.category.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.R
import com.sihabudin.itask.addCategory.fragment.AddCategoryFragment
import com.sihabudin.itask.category.adapter.CategoryListAdapter
import com.sihabudin.itask.category.viewModel.CategoryViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.utils.CustomToast
import com.sihabudin.itask.databinding.FragmentCategoryListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryListFragment : Fragment() {
    private val categoryViewModel: CategoryViewModel by viewModel()
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabCreateCat.setOnClickListener {
            val addCategory = AddCategoryFragment()
            addCategory.show(childFragmentManager, "ADD CATEGORY")
        }
        bindingData()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().popBackStack()

        }
        return true
    }

    private fun bindingData() {
        val categoryListAdapter =
            CategoryListAdapter { category, isActive -> onSwitchChange(category, isActive) }

        categoryViewModel.getAllCategory.observe(viewLifecycleOwner, { categories ->
            if (categories != null) {
                categoryListAdapter.setData(categories)
            }
        })

        with(binding.rvCategoryList) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = categoryListAdapter


        }

        categoryListAdapter.onItemClick = { selectedData ->
            val openEditCategory =
                CategoryListFragmentDirections.actionCategoryListFragment2ToEditCategoryFragment()
            openEditCategory.idCategory = selectedData.id_category
            findNavController().navigate(openEditCategory)
        }
    }

    private fun onSwitchChange(category: CategoryModel, isActive: Boolean) {
        category.isActive = !isActive
        categoryViewModel.updateCategory(category)
        CustomToast().activeNonActiveToast(requireContext(), !isActive)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false
    }


}