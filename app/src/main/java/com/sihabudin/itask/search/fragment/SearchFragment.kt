package com.sihabudin.itask.search.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.onQueryTextChanged
import com.sihabudin.itask.databinding.FragmentSearchBinding
import com.sihabudin.itask.search.adapter.SearchAdapter
import com.sihabudin.itask.search.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private val args by navArgs<SearchFragmentArgs>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel()
    private val searchAdapter = SearchAdapter { task, status -> onCheckedChanged(task, status) }

    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false

        val searchItem = menu.findItem(R.id.action_search)

        searchView = searchItem.actionView as SearchView
        searchItem.expandActionView()
        searchView.onQueryTextChanged {
            searchTask(it)
        }


    }

    private fun searchTask(query:String){
        searchAdapter.onItemClick = { task ->
            val openDetailTask =
                SearchFragmentDirections.actionSearchFragmentToDetailTaskFragment3()
            openDetailTask.idTask = task.id_task
            findNavController().navigate(openDetailTask)
        }

        searchViewModel.getSearchTask("%$query%").observe(viewLifecycleOwner, { task ->
            if (task != null) {
                searchAdapter.setData(task)
            }

        })
        with(binding.rvTaskSearch) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().popBackStack()
        }
        return true
    }
    private fun onCheckedChanged(task: TaskWithCategoryModel, status: String) {
        searchViewModel.updateTask(task)
        searchViewModel.updateAllSubTask(status,task.id_task)
    }


}