package com.sihabudin.itask.taskByCategory.viewModel

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.TaskModel
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.core.utils.Converter
import java.util.*

class TaskByCategoryViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    fun getTaskByCategory(idCategory: Int) =
        LiveDataReactiveStreams.fromPublisher(taskUseCase.getTaskByIdCategory(idCategory))

    fun updateTask(task: TaskWithCategoryModel) {
        val taskModel: TaskModel = TaskModel(
            id_task = task.id_task,
            title = task.title,
            detail = "",
            due_date = task.due_date,
            isSetDate = task.isSetDate,
            status = Common.setCompletedTask(task.status),
            createDate = task.createDate,
            createBy = "system",
            updateDate = Converter.toDate(Calendar.getInstance().timeInMillis)!!,
            updateBy = "system",
            id_category = task.id_category,
            isAlarm = task.isAlarm,
            idAlarm = task.idAlarm

        )
        taskUseCase.updateTask(taskModel)


    }

    fun updateAllSubTask(status: String, idTask: Long) =
        taskUseCase.updateAllSubtask(status, idTask)


}