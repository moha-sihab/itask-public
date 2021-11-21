package com.sihabudin.itask.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sihabudin.itask.databinding.FragmentHomeBinding
import com.sihabudin.itask.home.adapter.HomeAdapter
import com.sihabudin.itask.home.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabCreate.setOnClickListener {
            val addTask = HomeFragmentDirections.actionHomeFragmentToTaskNavigation()
            findNavController().navigate(addTask)
        }
        if (activity != null) {

            bindingData()


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingData() {
        val homeAdapter = HomeAdapter()


        homeViewModel.getCategoryWithCountTask.observe(viewLifecycleOwner, { categories ->

            if (categories != null) {
                homeAdapter.setData(categories)
            }
        })


        with(binding.rvHome) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        homeAdapter.onItemClick = { selectedData ->
            val taskByCategory = HomeFragmentDirections.actionHomeFragmentToTaskByCategoryFragment()
            taskByCategory.category = selectedData.category
            taskByCategory.idCategory = selectedData.id_category
            findNavController().navigate(taskByCategory)
        }

    }


}



