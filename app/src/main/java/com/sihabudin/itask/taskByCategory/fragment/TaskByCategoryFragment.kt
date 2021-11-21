package com.sihabudin.itask.taskByCategory.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.databinding.FragmentTaskByCategoryBinding
import com.sihabudin.itask.taskByCategory.adapter.TaskByCategoryAdapter
import com.sihabudin.itask.taskByCategory.viewModel.TaskByCategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TaskByCategoryFragment : Fragment() {

    private val args by navArgs<TaskByCategoryFragmentArgs>()
    private var _binding: FragmentTaskByCategoryBinding? = null
    private val taskByCategoryViewModel: TaskByCategoryViewModel by viewModel()
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskByCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().popBackStack()
        }
        return true
    }

    private fun bindingData() {

        binding.titleTaskByCat.text = args.category

        val taskByCategoryAdapter =
            TaskByCategoryAdapter { task, status -> onCheckedChanged(task, status) }
        taskByCategoryViewModel.getTaskByCategory(args.idCategory)
            .observe(viewLifecycleOwner, { task ->
                if (task != null) {
                    taskByCategoryAdapter.setData(task)
                }
            })



        with(binding.rvTaskCat) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = taskByCategoryAdapter
        }

        taskByCategoryAdapter.onItemClick = {
            val openDetailTask =
                TaskByCategoryFragmentDirections.actionTaskByCategoryFragmentToDetailTaskFragment3()
            openDetailTask.idTask = it.id_task
            findNavController().navigate(openDetailTask)
        }

    }

    private fun onCheckedChanged(task: TaskWithCategoryModel, status: String) {
        updateTask(task)

    }

    fun updateTask(task: TaskWithCategoryModel) {
        taskByCategoryViewModel.updateTask(task)
        taskByCategoryViewModel.updateAllSubTask("completed", task.id_task)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false
    }

}