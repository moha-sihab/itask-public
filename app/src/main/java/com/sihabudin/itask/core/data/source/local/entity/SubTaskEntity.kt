package com.sihabudin.itask.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtask")
data class SubTaskEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id_subtask")
    var id_subtask: Int,
    @ColumnInfo(name="id_task")
    var id_task: Long,
    @ColumnInfo(name="subtask")
    var subtask: String,
    @ColumnInfo(name="status")
    var status: String
)
