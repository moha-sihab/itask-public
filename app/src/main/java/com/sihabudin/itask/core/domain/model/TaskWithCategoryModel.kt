package com.sihabudin.itask.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TaskWithCategoryModel(
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
):Parcelable
