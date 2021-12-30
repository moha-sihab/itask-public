package com.sihabudin.itask.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormater {
    fun SimpleDateFormat.formatterDateFull() : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH)
    fun SimpleDateFormat.formatterDate() : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
    fun SimpleDateFormat.formatterDisplayDate() : SimpleDateFormat = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
    fun SimpleDateFormat.formatterDisplayDateFull() : SimpleDateFormat = SimpleDateFormat("EEE, dd MMM yyyy, HH:mm",Locale.ENGLISH)
    fun SimpleDateFormat.formatterTime() : SimpleDateFormat = SimpleDateFormat("HH:mm",Locale.ENGLISH)
    fun SimpleDateFormat.formatterMonth() : SimpleDateFormat = SimpleDateFormat("MMM",Locale.ENGLISH)
    fun fromStringToDateLong(dateString : String) : Long {
        var vDateLong : Long = 0
         try {
             val vDateFormat: SimpleDateFormat =SimpleDateFormat().formatterDateFull()
             var vDate: Date
             vDateFormat.parse(dateString).also { vDate = it }
             vDateLong  = Converter.fromDate(vDate)!!
         }
         catch (ex : Exception){
             vDateLong = 0
         }


        return  vDateLong
    }
    fun stringDate(date: Date) : String
    {
        val vDateFormat: SimpleDateFormat = SimpleDateFormat().formatterDate()
        var vDateResult = ""
        vDateResult = vDateFormat.format(date).toString()
        return vDateResult
    }

    fun stringDateDisplay(date: Date) : String
    {
        val vDateFormat: SimpleDateFormat = SimpleDateFormat().formatterDisplayDate()
        var vDateResult = ""
        vDateResult = vDateFormat.format(date).toString()
        return vDateResult
    }
    fun stringMonth(date: Date) : String{
        val vMonthFormat: SimpleDateFormat = SimpleDateFormat().formatterMonth()
        var vMonthResult = ""
        vMonthResult = vMonthFormat.format(date).toString()
        return vMonthResult
    }
    fun stringTime(date: Date) : String
    {
        val vTimeFormat : SimpleDateFormat = SimpleDateFormat().formatterTime()
        var vTimeResult = ""
        vTimeResult = vTimeFormat.format(date).toString()
        return vTimeResult
    }
    fun stringDateWithHourMinute(date: Date) : String
    {
        val vDateFormat: SimpleDateFormat = SimpleDateFormat().formatterDisplayDateFull()
        var vDateResult = ""
        vDateResult = vDateFormat.format(date).toString()
        return vDateResult


    }
}