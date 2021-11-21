package com.sihabudin.itask.core.domain.usecase


import com.sihabudin.itask.core.domain.model.*
import io.reactivex.Flowable

interface TaskUseCase {
    fun getAllTask(): Flowable<List<TaskModel>>
    fun getAllCategory(): Flowable<List<CategoryModel>>
    fun getActiveCategory(): Flowable<List<CategoryModel>>
    fun getTaskById(id: Long): Flowable<List<TaskWithCategoryModel>>
    fun getTaskByIdCategory(id: Int): Flowable<List<TaskWithCategoryModel>>
    fun getCategoryById(id: Int): Flowable<List<CategoryModel>>
    fun getCategoryWithCountTask(): Flowable<List<CategoryWithCountTaskModel>>
    fun getSubTaskByIdTask(id: Long): Flowable<List<SubTaskModel>>
    fun getTaskByStatus(status: String): Flowable<List<TaskWithCategoryModel>>
    fun getTaskToday(): Flowable<List<TaskWithCategoryModel>>
    fun getTaskTomorrow(): Flowable<List<TaskWithCategoryModel>>
    fun getTaskNextWeek(): Flowable<List<TaskWithCategoryModel>>
    fun getTaskHistory(): Flowable<List<TaskWithCategoryModel>>
    fun getSearchTask(title: String): Flowable<List<TaskWithCategoryModel>>
    fun getTotalTask(): Flowable<Int>
    fun getNextIdAlarm() : Flowable<Int>
    fun insertTask(task: TaskModel)
    fun insertCategory(category: CategoryModel)
    fun insertSubTask(subtask: SubTaskModel)
    fun updateTask(task: TaskModel)
    fun updateCategory(category: CategoryModel)
    fun updateAllSubtask(status: String, idTask: Long)
    fun deleteSubtask(subtask: SubTaskModel)

}