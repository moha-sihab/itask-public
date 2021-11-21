package com.sihabudin.itask.history.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.databinding.FragmentHistoryBinding
import com.sihabudin.itask.history.adapter.HistoryAdapter
import com.sihabudin.itask.history.viewModel.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : Fragment() {

    private val historyViewModel: HistoryViewModel by viewModel()
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingData() {
        val historyAdapter = HistoryAdapter { task, status -> onCheckedChanged(task, status) }
        historyViewModel.getTaskHistory().observe(viewLifecycleOwner, { task ->
            if (task != null) {

                historyAdapter.setData(task)
            }
        })

        with(binding.rvTaskHistory) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = historyAdapter
        }

        historyAdapter.onItemClick = {
            val openDetailTask =
                HistoryFragmentDirections.actionHistoryFragmentToDetailTaskFragment3()
            openDetailTask.idTask = it.id_task
            findNavController().navigate(openDetailTask)
        }


    }

    private fun onCheckedChanged(task: TaskWithCategoryModel, status: String) {
        updateTask(task)

    }


    private fun updateTask(task: TaskWithCategoryModel) {
        historyViewModel.updateTask(task)
        historyViewModel.updateAllSubTask("completed", task.id_task)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false
    }

}