package com.sihabudin.itask.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


//https://android--code.blogspot.com/2019/07/android-kotlin-room-typeconverter-date.html
@Entity(tableName = "task")
data class TaskEntity (
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name="id_task")
    var id_task: Long,

    @ColumnInfo(name="title")
    var title: String,

    @ColumnInfo(name="detail")
    var detail: String,

    @ColumnInfo(name="due_date")
    var due_date: Date?,

    @ColumnInfo(name="isSetDate")
    var isSetDate: Boolean,

    @ColumnInfo(name="isAlarm")
    var isAlarm: Boolean,

    @ColumnInfo(name="idAlarm")
    var idAlarm: Int,

    @ColumnInfo(name="id_category")
    var  id_category: Int,

    @ColumnInfo(name="status")
    var status: String,

    @ColumnInfo(name="createDate")
    var createDate: Date,

    @ColumnInfo(name="createBy")
    var createBy: String,

    @ColumnInfo(name="updateDate")
    var updateDate: Date,

    @ColumnInfo(name="updateBy")
    var updateBy: String


)