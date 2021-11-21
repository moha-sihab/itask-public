package com.sihabudin.itask.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubTaskModel(
    var id_subtask: Int,
    var id_task: Long,
    var subtask: String,
    var status: String
) : Parcelable
