package com.sihabudin.itask.core.data

import android.util.Log
import com.sihabudin.itask.core.data.source.local.LocalDataSource
import com.sihabudin.itask.core.domain.model.*
import com.sihabudin.itask.core.domain.repository.ITaskRepository
import com.sihabudin.itask.core.utils.AppExecutors
import com.sihabudin.itask.core.utils.DataMapper
import com.sihabudin.itask.core.utils.ResponseInfoEnum
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TaskRepository constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ITaskRepository {

    private val tag = "TaskRepository :"

    companion object {
        private const val PAGE_SIZE = 10
        const val PLACEHOLDERS = true
    }

    override fun getAllTask(): Flowable<List<TaskModel>> {
        return localDataSource.getAllTask().map {
            DataMapper.mapTaskEntitiesToDomain(it)
        }
    }

    override fun getAllCategory(): Flowable<List<CategoryModel>> {
        return localDataSource.getAllCategory().map {
            DataMapper.mapCategoryEntitiesToDomain(it)
        }
    }

    override fun getActiveCategory(): Flowable<List<CategoryModel>> {
        return localDataSource.getActiveCategory().map {
            DataMapper.mapCategoryEntitiesToDomain(it)
        }
    }

    override fun getTaskById(id: Long): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskById(id).map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getTaskByIdCategory(id: Int): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskByIdCategory(id).map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getCategoryById(id: Int): Flowable<List<CategoryModel>> {
        return localDataSource.getCategoryById(id).map {
            DataMapper.mapCategoryEntitiesToDomain(it)
        }
    }

    override fun getCategoryWithCountTask(): Flowable<List<CategoryWithCountTaskModel>> {
        return localDataSource.getCategoryWithCountTask().map {
            DataMapper.mapCategoryWithCountTaskToDomain(it)
        }
    }

    override fun getSubTaskByIdTask(id: Long): Flowable<List<SubTaskModel>> {
        return localDataSource.getSubTaskByIdTask(id).map {
            DataMapper.mapSubTaskEntitiesToDomain(it)
        }
    }

    override fun getTaskByStatus(status: String): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskByStatus(status).map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getTaskToday(): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskToday().map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getTaskTomorrow(): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskTomorrow().map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getTaskNextWeek(): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskNextWeek().map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getTaskHistory(): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getTaskHistory().map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getSearchTask(title: String): Flowable<List<TaskWithCategoryModel>> {
        return localDataSource.getSearchTask(title).map {
            DataMapper.mapTaskWithCategoryEntitiesToDomain(it)
        }
    }

    override fun getTotalTask(): Flowable<Int> {
        return localDataSource.getTotalTask()

    }

    override  fun getNextIdAlarm() : Flowable<Int>{
        return localDataSource.getNextIdAlarm()
    }

    override fun insertTask(task: TaskModel) {
        val taskEntity = DataMapper.mapTaskDomainToEntity(task)
        localDataSource.insertTask(taskEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(tag, ResponseInfoEnum.SUCCESS.toString()) },
                { Log.d(tag, ResponseInfoEnum.ERROR.toString()) }
            )
    }

    override fun insertCategory(category: CategoryModel) {

        val categoryEntity = DataMapper.mapCategoryDomainToEntity(category)
        localDataSource.insertCategory(categoryEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(tag, ResponseInfoEnum.SUCCESS.toString()) },
                { Log.d(tag, ResponseInfoEnum.ERROR.toString()) }
            )


    }

    override fun insertSubTask(subtask: SubTaskModel) {
        val subTaskEntity = DataMapper.mapSubTaskDomainToEntity(subtask)
        localDataSource.insertSubTask(subTaskEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(tag, ResponseInfoEnum.SUCCESS.toString()) },
                { Log.d(tag, ResponseInfoEnum.ERROR.toString()) }
            )
    }

    override fun updateTask(task: TaskModel) {
        val taskEntity = DataMapper.mapTaskDomainToEntity(task)
        appExecutors.diskIO().execute { localDataSource.updateTask(taskEntity) }
    }

    override fun updateCategory(category: CategoryModel) {
        val categoryEntity = DataMapper.mapCategoryDomainToEntity(category)
        appExecutors.diskIO().execute { localDataSource.updateCategory(categoryEntity) }
    }

    override fun updateAllSubtask(status: String, idTask: Long) {
        appExecutors.diskIO().execute { localDataSource.updateAllSubtask(status, idTask) }
    }

    override fun deleteSubtask(subtask: SubTaskModel) {
        val subTaskEntity = DataMapper.mapSubTaskDomainToEntity(subtask)
        appExecutors.diskIO().execute { localDataSource.deleteSubtask(subTaskEntity) }
    }

}