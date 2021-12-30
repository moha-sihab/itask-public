package com.sihabudin.itask.core.domain.usecase

import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.core.domain.model.TaskModel
import com.sihabudin.itask.core.domain.repository.ITaskRepository
import io.reactivex.Flowable

class TaskInteractor(private val taskRepository: ITaskRepository) : TaskUseCase {

    override fun getAllTask() = taskRepository.getAllTask()
    override fun getAllCategory() = taskRepository.getAllCategory()
    override fun getActiveCategory() = taskRepository.getActiveCategory()
    override fun getTaskById(id: Long) = taskRepository.getTaskById(id)
    override fun getTaskByIdCategory(id: Int) = taskRepository.getTaskByIdCategory(id)
    override fun getCategoryById(id: Int) = taskRepository.getCategoryById(id)
    override fun getCategoryWithCountTask() = taskRepository.getCategoryWithCountTask()
    override fun getSubTaskByIdTask(id: Long) = taskRepository.getSubTaskByIdTask(id)
    override fun getTaskByStatus(status: String) = taskRepository.getTaskByStatus(status)
    override fun getTaskToday() = taskRepository.getTaskToday()
    override fun getTaskTodayStatusOpen() = taskRepository.getTaskTodayStatusOpen()
    override fun getTaskTomorrow() = taskRepository.getTaskTomorrow()
    override fun getTaskNextWeek() = taskRepository.getTaskNextWeek()
    override fun getTaskHistory() = taskRepository.getTaskHistory()
    override fun getSearchTask(title: String) = taskRepository.getSearchTask(title)
    override fun getTotalTask() = taskRepository.getTotalTask()
    override fun getNextIdAlarm() =taskRepository.getNextIdAlarm()
    override fun insertTask(task: TaskModel) = taskRepository.insertTask(task)
    override fun insertCategory(category: CategoryModel) = taskRepository.insertCategory(category)
    override fun insertSubTask(subtask: SubTaskModel) = taskRepository.insertSubTask(subtask)
    override fun updateTask(task: TaskModel) = taskRepository.updateTask(task)
    override fun updateAllSubtask(status: String, idTask: Long) =
        taskRepository.updateAllSubtask(status, idTask)

    override fun updateCategory(category: CategoryModel) = taskRepository.updateCategory(category)
    override fun deleteSubtask(subtask: SubTaskModel) = taskRepository.deleteSubtask(subtask)

}