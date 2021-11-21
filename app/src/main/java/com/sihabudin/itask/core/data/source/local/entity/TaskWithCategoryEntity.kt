package com.sihabudin.itask.core.data.source.local.entity

import java.util.*

data class TaskWithCategoryEntity(
    var id_task: Long,
    var title: String,
    var detail: String,
    var due_date: Date?,
    var isSetDate: Boolean,
    var isAlarm: Boolean,
    var idAlarm: Int,
    var id_category: Int,
    var status: String,
    var createDate: Date,
    var createBy: String,
    var updateDate: Date,
    var updateBy: String,
    var category: String,
    var colorLabel: String
)
