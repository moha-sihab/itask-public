package com.sihabudin.itask.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName ="category")
data class CategoryEntity (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id_category")
    var id_category: Int,

    @ColumnInfo(name="category")
    var category: String,

    @ColumnInfo(name="isActive")
    var isActive: Boolean,

    @ColumnInfo(name="colorLabel")
    var colorLabel: String,

    @ColumnInfo(name="createDate")
    var createDate: Date,

    @ColumnInfo(name="createBy")
    var createBy: String,

    @ColumnInfo(name="updateDate")
    var updateDate: Date,

    @ColumnInfo(name="updateBy")
    var updateBy: String


)