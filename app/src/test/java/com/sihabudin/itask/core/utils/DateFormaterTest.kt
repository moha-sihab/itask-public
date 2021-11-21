package com.sihabudin.itask.core.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateFormaterTest{
    @Test
    fun fromStringToDateLong(){
        val dateString ="22-09-2021 06:30:00"
        val result  = DateFormater.fromStringToDateLong(dateString)
        assertThat(result).isEqualTo(1632267000000)
    }

    @Test
    fun stringDate(){
        val dateString ="22-09-2021 06:30:00"
        val convertDateStringToLong  = DateFormater.fromStringToDateLong(dateString)
        val convertToDate = Converter.toDate(convertDateStringToLong!!)
        val result  = convertToDate?.let { DateFormater.stringDate(it) }
        assertThat(result).isEqualTo("2021-09-22")

    }

    @Test
    fun stringMonth(){
        val dateString ="22-09-2021 06:30:00"
        val convertDateStringToLong  = DateFormater.fromStringToDateLong(dateString)
        val convertToDate = Converter.toDate(convertDateStringToLong!!)
        val result  = convertToDate?.let { DateFormater.stringMonth(it) }
        assertThat(result).isEqualTo("Sep")

    }

    @Test
    fun stringDateDisplay(){
        val dateString ="22-09-2021 06:30:00"
        val convertDateStringToLong  = DateFormater.fromStringToDateLong(dateString)
        val convertToDate = Converter.toDate(convertDateStringToLong!!)
        val result  = convertToDate?.let { DateFormater.stringDateDisplay(it) }
        assertThat(result).isEqualTo("22 Sep 2021")

    }

    @Test
    fun stringTime(){
        val dateString ="22-09-2021 06:30:00"
        val convertDateStringToLong  = DateFormater.fromStringToDateLong(dateString)
        val convertToDate = Converter.toDate(convertDateStringToLong!!)
        val result  = convertToDate?.let { DateFormater.stringTime(it) }
        assertThat(result).isEqualTo("06:30")
    }

    @Test
    fun stringDateWithHourMinute(){
        val dateString ="22-09-2021 06:30:00"
        val convertDateStringToLong  = DateFormater.fromStringToDateLong(dateString)
        val convertToDate = convertDateStringToLong?.let { Converter.toDate(it) }
        val result = convertToDate?.let { DateFormater.stringDateWithHourMinute(it) }
        assertThat(result).isEqualTo("Wed, 22 Sep 2021, 06:30")

    }
}