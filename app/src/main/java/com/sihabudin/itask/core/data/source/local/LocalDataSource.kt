package com.sihabudin.itask.core.data.source.local


import com.sihabudin.itask.core.data.source.local.entity.*
import com.sihabudin.itask.core.data.source.local.room.TaskDao
import io.reactivex.Flowable

class LocalDataSource(private val taskDao: TaskDao) {

    fun getAllTask(): Flowable<List<TaskEntity>> = taskDao.getAllTask()

    fun getAllCategory(): Flowable<List<CategoryEntity>> = taskDao.getAllCategory()

    fun getActiveCategory(): Flowable<List<CategoryEntity>> = taskDao.getActiveCategory()

    fun getTaskById(id: Long): Flowable<List<TaskWithCategoryEntity>> = taskDao.getTaskById(id)

    fun getTaskByIdCategory(id: Int): Flowable<List<TaskWithCategoryEntity>> =
        taskDao.getTaskByIdCategory(id)

    fun getTaskByStatus(status: String): Flowable<List<TaskWithCategoryEntity>> =
        taskDao.getTaskByStatus(status)

    fun getTaskToday(): Flowable<List<TaskWithCategoryEntity>> = taskDao.getTaskToday()

    fun getTaskTomorrow(): Flowable<List<TaskWithCategoryEntity>> = taskDao.getTaskTomorrow()

    fun getTaskNextWeek(): Flowable<List<TaskWithCategoryEntity>> = taskDao.getTaskNextWeek()

    fun getTaskHistory(): Flowable<List<TaskWithCategoryEntity>> = taskDao.getTaskHistory()

    fun getSearchTask(title: String): Flowable<List<TaskWithCategoryEntity>> =
        taskDao.getSearchTask(title)

    fun getTotalTask():Flowable<Int> = taskDao.getTotalTask()

    fun getNextIdAlarm() : Flowable<Int> = taskDao.getNextIdAlarm()

    fun getCategoryById(id: Int): Flowable<List<CategoryEntity>> = taskDao.getCategoryById(id)

    fun getCategoryWithCountTask(): Flowable<List<CategoryWithCountTaskEntity>> =
        taskDao.getCategoryWithCountTask()

    fun getSubTaskByIdTask(id: Long): Flowable<List<SubTaskEntity>> = taskDao.getSubTaskByIdTask(id)

    fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    fun insertCategory(category: CategoryEntity) = taskDao.insertCategory(category)

    fun insertSubTask(subtask: SubTaskEntity) = taskDao.insertSubTask(subtask)

    fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    fun updateAllSubtask(status: String, idTask: Long) = taskDao.updateAllSubtask(status, idTask)

    fun updateCategory(category: CategoryEntity) = taskDao.updateCategory(category)

    fun deleteSubtask(subtask: SubTaskEntity) = taskDao.deleteSubtask(subtask)

}