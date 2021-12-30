package com.sihabudin.itask.core.data.source.local.room

import androidx.room.*
import com.sihabudin.itask.core.data.source.local.entity.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TaskDao {
    @Query("select * from task")
    fun getAllTask(): Flowable<List<TaskEntity>>

    @Query("select * from category")
    fun getAllCategory(): Flowable<List<CategoryEntity>>

    @Query("select * from category where isActive=1")
    fun getActiveCategory(): Flowable<List<CategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where  task.id_task=:id")
    fun getTaskById(id: Long): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where task.id_category=:id")
    fun getTaskByIdCategory(id: Int): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where task.status=:status")
    fun getTaskByStatus(status: String): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where strftime('%Y-%m-%d', datetime(task.due_date/1000, 'unixepoch','localtime'))=date('now','localtime')")
    fun getTaskToday(): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where strftime('%Y-%m-%d', datetime(task.due_date/1000, 'unixepoch','localtime'))=date('now','localtime') and status='open'")
    fun getTaskTodayStatusOpen(): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where strftime('%Y-%m-%d', datetime(task.due_date/1000, 'unixepoch','localtime'))=date('now','+1 day','localtime')")
    fun getTaskTomorrow(): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where strftime('%Y-%m-%d', datetime(task.due_date/1000, 'unixepoch','localtime'))=date('now','+7 day','localtime')")
    fun getTaskNextWeek(): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where strftime('%Y-%m-%d', datetime(task.due_date/1000, 'unixepoch','localtime')) < date('now','localtime')")
    fun getTaskHistory(): Flowable<List<TaskWithCategoryEntity>>

    @Query("select task.*,category.category,category.colorLabel from task inner join category on task.id_category = category.id_category where task.title like :title")
    fun getSearchTask(title: String): Flowable<List<TaskWithCategoryEntity>>

    @Query("select count(*) from task")
    fun getTotalTask() : Flowable<Int>

    @Query("select count(*)+1 from task")
    fun getNextIdAlarm() : Flowable<Int>

    @Query("select * from category where id_category=:id")
    fun getCategoryById(id: Int): Flowable<List<CategoryEntity>>

    @Query("select distinct category.id_category,category.category, category.colorLabel,count(task.id_task) as totalTask from category left join task on category.id_category = task.id_category and task.status='open'  group by category.id_category ")
    fun getCategoryWithCountTask(): Flowable<List<CategoryWithCountTaskEntity>>

    @Query("select * from subtask where id_task=:id")
    fun getSubTaskByIdTask(id: Long): Flowable<List<SubTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubTask(subtask: SubTaskEntity): Completable

    @Update
    fun updateTask(task: TaskEntity)

    @Update
    fun updateCategory(category: CategoryEntity)

    @Query("update subtask set status=:status where id_task = :idTask")
    fun updateAllSubtask(status: String, idTask: Long)

    @Delete
    fun deleteSubtask(subtask: SubTaskEntity)

}