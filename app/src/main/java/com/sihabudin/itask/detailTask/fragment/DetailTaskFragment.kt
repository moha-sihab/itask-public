package com.sihabudin.itask.detailTask.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.sihabudin.itask.R
import com.sihabudin.itask.R.string
import com.sihabudin.itask.R.style
import com.sihabudin.itask.addTask.fragment.AddSubTaskFragment
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.*
import com.sihabudin.itask.databinding.FragmentDetailTaskBinding
import com.sihabudin.itask.detailTask.adapter.SubTaskDetailAdapter
import com.sihabudin.itask.detailTask.viewModel.DetailTaskViewModel
import com.sihabudin.itask.sharedViewModel.SharedViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailTaskFragment : DialogFragment() {

    private var _binding: FragmentDetailTaskBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val detailTaskViewModel: DetailTaskViewModel by viewModel()
    private val binding get() = _binding!!
    private lateinit var categorySelected: CategoryModel
    private var mapDate: HashMap<String, String> = HashMap<String, String>()
    private var subTasks: MutableList<SubTaskModel> = mutableListOf()
    private var tasks: MutableList<TaskWithCategoryModel> = mutableListOf()
    private val args by navArgs<DetailTaskFragmentArgs>()
    private var firstBinding: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnOpenDetailSubTask.setOnClickListener {
            val openSubTask = AddSubTaskFragment()

            openSubTask.show(childFragmentManager, "Add Sub Task")
        }
        binding.cardSelectDateDetailTask.setOnClickListener {
            openDatePicker()
        }
        binding.cardSelectTimeDetailTask.setOnClickListener {
            openTimePicker()
        }

        binding.btnUpdateTask.setOnClickListener {
            updateTask()
        }

        binding.btnCompleteTask.setOnClickListener {
            completeTask()
        }
        clearForm()
        bindingData()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTaskBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        clearForm()
        _binding = null
    }

    override fun getTheme(): Int {
        return style.DialogTheme
    }

    private fun updateTask() {
        if (validationInput()) {
            updateInitiation("open")
        }

    }

    private fun completeTask() {
        if (validationInput()) {
            updateInitiation("completed")
        }

    }

    private fun updateInitiation(status: String) {
        val vDateSelected: String = mapDate["date"].toString() + "-" +
                mapDate["month"] + "-" +
                mapDate["year"] + " " +
                mapDate["hour"] + ":" +
                mapDate["minute"] + ":00"

        val vDueDate: Long = DateFormater.fromStringToDateLong(vDateSelected)!!

        val task = TaskWithCategoryModel(
            id_task = args.idTask,
            title = binding.txtTitleDetailTask.text.toString(),
            detail = "",
            due_date = Converter.toDate(vDueDate),
            isSetDate = true,
            status = status,
            createDate = tasks[0].createDate,
            createBy = tasks[0].createBy,
            updateDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!,
            updateBy = "system",
            id_category = categorySelected.id_category,
            isAlarm = binding.switchReminderDetailTask.isChecked,
            category = "",
            colorLabel = "",
            idAlarm = tasks[0].idAlarm
        )

        detailTaskViewModel.updateTask(task)
        subTasks.toObservable()
            .subscribeBy(
                onNext = {
                    it.id_task = args.idTask
                    it.status = status
                    detailTaskViewModel.insertSubTask(it)
                }
            )

        if (binding.switchReminderDetailTask.isChecked) {
           /* AlarmReceiver().setAlarm(
                requireContext(),
                vDueDate,
                tasks[0].idAlarm,
                binding.txtTitleDetailTask.text.toString()
            )*/
        } else {
              Log.e("cancel alarm id =",tasks[0].idAlarm.toString())
              AlarmReceiver().cancelAlarm(requireContext(),tasks[0].idAlarm)

        }


        if (status == "completed") {
            CustomToast().completeTaskToast(requireContext())
        } else {
            CustomToast().successSaveToast(requireContext())
        }

        clearForm()

    }

    private fun clearForm() {
        binding.txtTitleDetailTask.text?.clear()
        binding.tvDateDetailTask.text = ""
        binding.tvTimeDetailTask.text = ""
        binding.tvCategoryDetailTask.text = ""
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

        detailTaskViewModel.setSubTask(subTasks)
        sharedViewModel.setSubTask(subtaskModel)
        sharedViewModel.setCategorySelected(categorySelected)

    }


    private fun bindingData() {
        firstBinding()
        bindingCategorySelected()
        bindingSubTask()

    }

    private fun firstBinding() {
        if (!firstBinding) {
            categorySelected = CategoryModel(
                0,
                "",
                false,
                "",
                Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                "",
                Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                ""
            )
            detailTaskViewModel.getTaskDetail(args.idTask).observe(viewLifecycleOwner, { task ->
                tasks.addAll(task)
                val dueDate = DateFormater.stringDate(task[0].due_date!!)
                val dueDateDisplay = DateFormater.stringDateDisplay(task[0].due_date!!)
                val dueTime = DateFormater.stringTime(task[0].due_date!!)

                binding.txtTitleDetailTask.setText(task[0].title)
                binding.tvCategoryDetailTask.text = task.get(0).category
                binding.imageColorDetailTask.setImageResource(Common.transformColorLabel(task[0].colorLabel))
                binding.switchReminderDetailTask.isChecked = task[0].isAlarm

                categorySelected.category = task[0].category
                categorySelected.colorLabel = task[0].colorLabel
                categorySelected.id_category = task[0].id_category
                sharedViewModel.setCategorySelected(categorySelected)



                binding.tvDateDetailTask.text = dueDateDisplay
                binding.tvTimeDetailTask.text = dueTime
                mapDate["year"] = dueDate.substring(0, 4)
                mapDate["month"] = dueDate.substring(5, 7)
                mapDate["date"] = dueDate.substring(8, 10)
                mapDate["hour"] = dueTime.substring(0, 2)
                mapDate["minute"] = dueTime.substring(3, 5)

                if (task[0].status == "completed") {
                    binding.btnCompleteTask.visibility = View.INVISIBLE
                    binding.btnUpdateTask.visibility = View.INVISIBLE
                }

                firstBinding = true
            })

            detailTaskViewModel.getSubTaskByIdTask(args.idTask)
                .observe(viewLifecycleOwner, { subtask ->
                    subTasks.addAll(subtask)
                    detailTaskViewModel.setSubTask(subTasks)
                })

        }
    }

    private fun bindingCategorySelected() {

        sharedViewModel.categorySelected.observe(viewLifecycleOwner, { category ->
            categorySelected = category
            Log.e("Category Selected=", categorySelected.category)
            if (categorySelected.id_category > 0) {

                binding.tvCategoryDetailTask.text = categorySelected.category
                binding.imageColorDetailTask.setImageResource(
                    Common.transformColorLabel(
                        categorySelected.colorLabel
                    )
                )
            } else {

                binding.tvCategoryDetailTask.setText(string.selectCategory)
            }
        })
    }

    private fun bindingSubTask() {
        val subTaskAdapter = SubTaskDetailAdapter()
        sharedViewModel.subTask.observe(viewLifecycleOwner, { subTask ->
            if (subTask.subtask != "") {
                subTasks.add(subTask)
                detailTaskViewModel.setSubTask(subTasks)
            }
        })

        detailTaskViewModel.getSubTask.observe(viewLifecycleOwner, { subTask ->
            if (subTask != null) {
                subTaskAdapter.setData(subTask.filter { it.subtask != "" })
            }
        })

        with(binding.rvDetailSubTask) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subTaskAdapter
            val itemDecoration = DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
            androidx.appcompat.content.res.AppCompatResources.getDrawable(
                this.context,
                R.drawable.item_devider
            )?.let { itemDecoration.setDrawable(it) }
            addItemDecoration(itemDecoration)
        }

        subTaskAdapter.onItemDeleteClick = {
            subTasks.remove(it)
            //  detailTaskViewModel.setSubTask(subTasks)
            subTaskAdapter.deleteItem(it)
            detailTaskViewModel.deleteSubTask(it)

            if (it.id_task > 0) {
                val subtaskModel = SubTaskModel(
                    id_task = 0,
                    subtask = "",
                    id_subtask = 0,
                    status = ""
                )
                subTasks = mutableListOf()

                detailTaskViewModel.setSubTask(subTasks)
                sharedViewModel.setSubTask(subtaskModel)
            }


        }
    }

    private fun onDeleted(subTaskModel: SubTaskModel) {
        subTasks.remove(subTaskModel)
        detailTaskViewModel.deleteSubTask(subTaskModel)
        detailTaskViewModel.setSubTask(subTasks)

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

            binding.tvTimeDetailTask.text = timeSelected

            Log.d("dateSelected =", timeSelected)
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

    private fun openDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
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


            binding.tvDateDetailTask.text = dateSelected

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

    private fun validationInput(): Boolean {
        var valid = true
        var message = ""
        if (binding.txtTitleDetailTask.text.toString() == "" || mapDate.isEmpty() || categorySelected.id_category == 0) {
            valid = false
            message = getString(string.messageValidInputTask)
            CustomToast().errorSaveToast(requireContext(), message)
        }

        return valid
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false
    }


}