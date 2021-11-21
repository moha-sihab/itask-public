package com.sihabudin.itask.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CategoryModel(
    var id_category: Int,
    var category: String,
    var isActive: Boolean,
    var colorLabel: String,
    var createDate: Date,
    var createBy: String,
    var updateDate: Date,
    var updateBy: String
):Parcelable