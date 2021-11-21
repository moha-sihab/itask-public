package com.sihabudin.itask.core.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.sihabudin.itask.R

class CustomToast {
    fun successSaveToast(context: Context) {
        DynamicToast.make(
            context, context.getString(R.string.messageSaveSuccess),
            AppCompatResources.getDrawable(context, R.drawable.adt_ic_success),
            ContextCompat.getColor(context, R.color.white),
            ContextCompat.getColor(context, R.color.primaryColor),
            Toast.LENGTH_LONG
        ).show()
    }
    fun errorSaveToast(context: Context, messageError : String) {
        DynamicToast.make(
            context, context.getString(R.string.messageSaveError) + ' '+messageError,
            AppCompatResources.getDrawable(context, R.drawable.adt_ic_error),
            ContextCompat.getColor(context, R.color.white),
            ContextCompat.getColor(context, R.color.red),
            Toast.LENGTH_LONG
        ).show()
    }
    fun cancelToast(context: Context) {
        DynamicToast.make(
            context, context.getString(R.string.messageCancel),
            AppCompatResources.getDrawable(context, R.drawable.adt_ic_success),
            ContextCompat.getColor(context, R.color.primaryColor),
            ContextCompat.getColor(context, R.color.yellow),
            Toast.LENGTH_LONG
        ).show()
    }

    fun completeTaskToast(context: Context) {
        DynamicToast.make(
            context, context.getString(R.string.messageComplete),
            AppCompatResources.getDrawable(context, R.drawable.adt_ic_success),
            ContextCompat.getColor(context, R.color.white),
            ContextCompat.getColor(context, R.color.primaryColor),
            Toast.LENGTH_LONG
        ).show()
    }

    fun activeNonActiveToast(context: Context,active: Boolean){
        var message = ""
        if(active){
            message = context.getString(R.string.messageActive)

        }
        else {
            message = context.getString(R.string.messageNotActive)
        }
        DynamicToast.make(
            context, message,
            AppCompatResources.getDrawable(context, R.drawable.adt_ic_success),
            ContextCompat.getColor(context, R.color.white),
            ContextCompat.getColor(context, R.color.primaryColor),
            Toast.LENGTH_LONG
        ).show()
    }
}