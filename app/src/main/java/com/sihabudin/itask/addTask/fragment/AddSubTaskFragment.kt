package com.sihabudin.itask.addTask.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.databinding.FragmentAddSubTaskBinding
import com.sihabudin.itask.sharedViewModel.SharedViewModel


class AddSubTaskFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddSubTaskBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var subTask: SubTaskModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subTask = SubTaskModel(0, 0, "", "")
        binding.btnAddSubTask.setOnClickListener {
            subTask.subtask = binding.txtInputSubTask.text.toString()
            subTask.status = "open"
            sharedViewModel.setSubTask(subTask)
            dismiss()
        }
        binding.btnCloseSubTask.setOnClickListener {
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSubTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        subTask = SubTaskModel(0, 0, "", "")
    }


}