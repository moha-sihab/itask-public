package com.sihabudin.itask.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sihabudin.itask.core.data.source.local.entity.CategoryEntity
import com.sihabudin.itask.core.data.source.local.entity.SubTaskEntity
import com.sihabudin.itask.core.data.source.local.entity.TaskEntity
import com.sihabudin.itask.core.utils.Converter

@Database(
    entities = [TaskEntity::class, CategoryEntity::class,  SubTaskEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun TaskDao() : TaskDao
}