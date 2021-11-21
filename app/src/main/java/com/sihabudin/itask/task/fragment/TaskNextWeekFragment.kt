package com.sihabudin.itask.task.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.databinding.FragmentTaskNextWeekBinding
import com.sihabudin.itask.task.adapter.TasksAdapter
import com.sihabudin.itask.task.viewModel.TaskNextWeekViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskNextWeekFragment : Fragment() {

    private val taskNextWeekViewModel: TaskNextWeekViewModel by viewModel()
    private var _binding: FragmentTaskNextWeekBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskNextWeekBinding.inflate(inflater, container, false)
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
        val tasksAdapter = TasksAdapter { task, status -> onCheckedChanged(task, status) }
        taskNextWeekViewModel.getTaskNextWeek().observe(viewLifecycleOwner, { tasks ->
            if (tasks != null) {
                tasksAdapter.setData(tasks)
            }
        })

        with(binding.rvTaskNextweek) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tasksAdapter
        }
        tasksAdapter.onItemClick = {
            val openDetailTask = TasksFragmentDirections.actionTasksFragmentToDetailTaskFragment3()
            openDetailTask.idTask = it.id_task
            findNavController().navigate(openDetailTask)
        }
    }

    private fun onCheckedChanged(task: TaskWithCategoryModel, status: String) {
        updateTask(task)

    }


    fun updateTask(task: TaskWithCategoryModel) {
        taskNextWeekViewModel.updateTask(task)
        taskNextWeekViewModel.updateAllSubTask(Common.setCompletedTask(task.status), task.id_task)
    }
}