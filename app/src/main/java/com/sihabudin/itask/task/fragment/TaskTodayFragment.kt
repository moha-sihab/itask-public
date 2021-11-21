package com.sihabudin.itask.task.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.AlarmReceiver
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.databinding.FragmentTaskTodayBinding
import com.sihabudin.itask.task.adapter.TasksAdapter
import com.sihabudin.itask.task.viewModel.TaskTodayViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TaskTodayFragment : Fragment() {

    private val taskTodayViewModel: TaskTodayViewModel by viewModel()
    private var _binding: FragmentTaskTodayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskTodayBinding.inflate(inflater, container, false)

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
        taskTodayViewModel.getTaskToday().observe(viewLifecycleOwner, { task ->
            if (task != null) {

                tasksAdapter.setData(task)
            }
        })

        with(binding.rvTaskToday) {
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
        if(Common.setCompletedTask(task.status) == "completed")
        {
            AlarmReceiver().cancelAlarm(requireContext(),task.idAlarm)
        }

    }

    fun updateTask(task: TaskWithCategoryModel) {
        taskTodayViewModel.updateTask(task)
        taskTodayViewModel.updateAllSubTask(Common.setCompletedTask(task.status), task.id_task)
    }


}