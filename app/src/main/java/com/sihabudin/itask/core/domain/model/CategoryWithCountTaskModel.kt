package com.sihabudin.itask.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryWithCountTaskModel(
    var id_category: Int,
    var category: String,
    var colorLabel: String,
    var totalTask : Int
):Parcelable