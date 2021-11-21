package com.sihabudin.itask.addTask.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.sihabudin.itask.R.*
import com.sihabudin.itask.addTask.adapter.SubTaskAdapter
import com.sihabudin.itask.addTask.viewModel.AddTaskViewModel
import com.sihabudin.itask.category.fragment.CategoryListSelectedFragment
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.core.domain.model.TaskModel
import com.sihabudin.itask.core.utils.*
import com.sihabudin.itask.core.utils.Common.Companion.transformColorLabel
import com.sihabudin.itask.databinding.FragmentAddTaskBinding
import com.sihabudin.itask.sharedViewModel.SharedViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddTaskFragment : DialogFragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addtaskViewModel: AddTaskViewModel by viewModel()
    private val binding get() = _binding!!
    private var categorySelected: CategoryModel = CategoryModel(
        0,
        "",
        false,
        "",
        Converter.toDate(Calendar.getInstance().timeInMillis)!!,
        "",
        Converter.toDate(Calendar.getInstance().timeInMillis)!!,
        "",
    )
    private var mapDate: HashMap<String, String> = HashMap<String, String>()
    private var subTasks: MutableList<SubTaskModel> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardSelectCategory.setOnClickListener {
            val selectCategory = CategoryListSelectedFragment()
            selectCategory.show(childFragmentManager, "SelectCategory")
        }

        binding.btnOpenSubTask.setOnClickListener {
            val openSubTask = AddSubTaskFragment()
            openSubTask.show(childFragmentManager, "Add Sub Task")

        }
        binding.cardSelectDate.setOnClickListener {
            openDatePicker()
        }
        binding.cardSelectTime.setOnClickListener {
            openTimePicker()
        }

        binding.btnAddTask.setOnClickListener {
            insertTask()

        }

        binding.imageCloseAddTask.setOnClickListener {
            clearForm()
            findNavController().popBackStack()
        }

        bindingData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun getTheme(): Int {
        return style.DialogTheme
    }

    private fun openDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(string.selectDate))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
                .build()


        datePicker.show(childFragmentManager, "OPEN DATE PICKER")

        datePicker.addOnPositiveButtonClickListener {


            val calendar = Calendar.getInstance()
            calendar.time = Date(it)

            mapDate["year"] = calendar.get(Calendar.YEAR).toString()
            mapDate["month"] = Common.addPrefixZero(calendar.get(Calendar.MONTH) + 1)
            mapDate["date"] = Common.addPrefixZero(calendar.get(Calendar.DAY_OF_MONTH))
            val dateSelected = Common.addPrefixZero(calendar.get(Calendar.DAY_OF_MONTH)) + " " +
                    DateFormater.stringMonth(calendar.time) + " " +
                    calendar.get(Calendar.YEAR).toString()



            binding.tvDateTask.text = dateSelected

            openTimePicker()
        }
        datePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
        }
        datePicker.addOnCancelListener {
            // Respond to cancel button click.
        }
        datePicker.addOnDismissListener {
            // Respond to dismiss events.
        }

    }

    private fun openTimePicker() {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText(getString(string.setTime))
            .setTheme(style.CustomThemeOverlay_TimePicker_Display)
            .build()

        timePicker.show(childFragmentManager, "OPEN TIME PICKER")

        timePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click.
            val timeSelected =
                timePicker.hour.toString() + " : " + Common.addPrefixZero(timePicker.minute)
            mapDate["hour"] = Common.addPrefixZero(timePicker.hour)
            mapDate["minute"] = Common.addPrefixZero(timePicker.minute)

            binding.tvTimeTask.text = timeSelected

        }
        timePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
        }
        timePicker.addOnCancelListener {
            // Respond to cancel button click.
        }
        timePicker.addOnDismissListener {
            // Respond to dismiss events.
        }


    }


    private fun bindingData() {
        bindingCategorySelected()
        bindingSubTask()
        bindingNextIdAlarm()
    }

    private fun bindingNextIdAlarm() {
        addtaskViewModel.getNextIdAlarm().subscribe { it -> addtaskViewModel.setIdAlarm(it) }
    }

    private fun bindingCategorySelected() {

        sharedViewModel.categorySelected.observe(viewLifecycleOwner, { category ->
            categorySelected = category

            if (categorySelected.id_category > 0) {

                binding.tvCategoryTask.text = categorySelected.category
                binding.imageColorTask.setImageResource(transformColorLabel(categorySelected.colorLabel))
            } else {

                binding.tvCategoryTask.setText(string.selectCategory)
            }
        })

    }

    private fun bindingSubTask() {
        val subTaskAdapter = SubTaskAdapter()



        sharedViewModel.subTask.observe(viewLifecycleOwner, { subTask ->
            if (subTask.subtask != "") {
                subTasks.add(subTask)
                addtaskViewModel.setSubTask(subTasks)
            }
        })

        addtaskViewModel.getSubTask.observe(viewLifecycleOwner, { subTask ->
            if (subTask != null) {
                subTaskAdapter.setData(subTask.filter { it.subtask != "" })
            }
        })



        with(binding.rvAddSubTask) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subTaskAdapter
            val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            getDrawable(
                this.context,
                drawable.item_devider
            )?.let { itemDecoration.setDrawable(it) }
            addItemDecoration(itemDecoration)
        }

        subTaskAdapter.onItemDeleteClick = {
            subTasks.remove(it)
            subTaskAdapter.deleteItem(it)
        }
    }


    private fun insertTask() {
        if (validationInput()) {
            val vDateSelected: String = mapDate["date"].toString() + "-" +
                    mapDate["month"] + "-" +
                    mapDate["year"] + " " +
                    mapDate["hour"] + ":" +
                    mapDate["minute"] + ":00"

            val vIdAlarm: String =
                mapDate["year"] + "" +
                        mapDate["hour"] + "" +
                        mapDate["minute"]

            val vDueDate: Long =
                if (vDateSelected != "") DateFormater.fromStringToDateLong(vDateSelected)!! else 0

            val vIdTask = getIdTask()

            var vGetIdAlarm: Int = 0

            addtaskViewModel.getIdAlarm.observe(viewLifecycleOwner,{
                it -> vGetIdAlarm = it
            })

            val taskModel = TaskModel(
                vIdTask,
                title = binding.txtTitleTask.text.toString(),
                detail = "",
                due_date = Converter.toDate(vDueDate),
                isSetDate = true,
                status = "open",
                createDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                createBy = "system",
                updateDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                updateBy = "system",
                id_category = categorySelected.id_category,
                isAlarm = binding.switchReminderTask.isChecked,
                idAlarm = vGetIdAlarm

            )


            try {
                addtaskViewModel.insertTask(taskModel)

                subTasks.toObservable()
                    .subscribeBy(
                        onNext = {
                            it.id_task = vIdTask
                            it.status = "open"
                            addtaskViewModel.insertSubTask(it)
                        }
                    )
                setUpAlarm(
                    binding.switchReminderTask.isChecked,
                    vDueDate,
                    vIdAlarm.toInt(),
                    binding.txtTitleTask.text.toString()
                )
                CustomToast().successSaveToast(requireContext())
            } catch (e: Exception) {
                CustomToast().errorSaveToast(requireContext(), e.message.toString())
            }

            clearForm()

        }


    }

    private fun setUpAlarm(alarm: Boolean, dateTime: Long, idAlarm: Int, message: String) {
        if (alarm) {
            AlarmReceiver().setAlarm(requireContext(), dateTime, idAlarm, message)
        }
    }

    private fun clearForm() {
        binding.txtTitleTask.text?.clear()
        binding.tvDateTask.text = ""
        binding.tvTimeTask.text = ""
        binding.tvCategoryTask.text = ""
        categorySelected = CategoryModel(
            0,
            "",
            false,
            "",
            Converter.toDate(Calendar.getInstance().timeInMillis)!!,
            "",
            Converter.toDate(Calendar.getInstance().timeInMillis)!!,
            "",
        )
        val subtaskModel = SubTaskModel(
            id_task = 0,
            subtask = "",
            id_subtask = 0,
            status = ""
        )
        subTasks = mutableListOf()
        addtaskViewModel.setSubTask(subTasks)
        sharedViewModel.setSubTask(subtaskModel)
        sharedViewModel.setCategorySelected(categorySelected)
    }

    private fun validationInput(): Boolean {
        var valid = true
        var message = ""
        if (binding.txtTitleTask.text.toString() == "" || mapDate.isEmpty() || categorySelected.id_category == 0) {
            valid = false
            message = getString(string.messageValidInputTask)
            CustomToast().errorSaveToast(requireContext(), message)
        }

        return valid
    }

    private fun getIdTask(): Long {
        return Calendar.getInstance().timeInMillis
    }

}

