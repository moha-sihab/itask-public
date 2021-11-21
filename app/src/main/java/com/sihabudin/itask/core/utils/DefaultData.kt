package com.sihabudin.itask.core.utils

import com.sihabudin.itask.core.domain.model.CategoryModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DefaultData {
    companion object {
        fun defaultDataCategory() : ArrayList<CategoryModel>{
            var cat: CategoryModel
            val catArrayList: ArrayList<CategoryModel> = ArrayList(0)
            val formatedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
            val formatedTime = SimpleDateFormat("HH:mm").format(Date())


            catArrayList.add(
                CategoryModel(
                    1,
                    "Personal",
                    true,
                    "Red",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system"
                )
            )

            catArrayList.add(
                CategoryModel(
                    2,
                    "Work",
                    true,
                    "Blue",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system"
                )
            )

            catArrayList.add(
                CategoryModel(
                    3,
                    "Home",
                    true,
                    "Purple",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system"
                )
            )

            catArrayList.add(
                CategoryModel(
                    4,
                    "Learn",
                    true,
                    "Yellow",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system",
                    Converter.toDate(Calendar.getInstance().timeInMillis)!!,
                    "system"
                )
            )
           return catArrayList
        }

    }

}