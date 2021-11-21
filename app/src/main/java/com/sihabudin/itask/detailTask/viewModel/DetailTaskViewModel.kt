package com.sihabudin.itask.detailTask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.core.domain.model.TaskModel
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase
import com.sihabudin.itask.core.utils.Converter
import java.util.*

class DetailTaskViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    private var _subTask = MutableLiveData<List<SubTaskModel>>()

    fun updateTask(task: TaskWithCategoryModel) {
        val taskModel = TaskModel(
            id_task = task.id_task,
            title = task.title,
            detail = "",
            due_date = task.due_date,
            isSetDate = task.isSetDate,
            status = task.status,
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

    fun getTaskDetail(taskId: Long) =
        LiveDataReactiveStreams.fromPublisher(taskUseCase.getTaskById(taskId))

    fun insertSubTask(subTaskModel: SubTaskModel) = taskUseCase.insertSubTask(subTaskModel)
    fun deleteSubTask(subTaskModel: SubTaskModel) = taskUseCase.deleteSubtask(subTaskModel)
    fun getSubTaskByIdTask(taskId: Long) =
        LiveDataReactiveStreams.fromPublisher(taskUseCase.getSubTaskByIdTask(taskId))

    val getSubTask: LiveData<List<SubTaskModel>> = _subTask
    fun setSubTask(subTask: MutableList<SubTaskModel>) {
        _subTask.postValue(subTask)
    }


}