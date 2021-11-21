package com.sihabudin.itask.core.utils

import com.sihabudin.itask.R

class Common {
    companion object{
        fun transformColorLabel(colorLabel : String) : Int
        {
            var resultDrawable : Int = R.drawable.gradient_blue
            when(colorLabel.lowercase())
            {
                "red"  -> resultDrawable = R.drawable.gradient_red
                "blue" -> resultDrawable =  R.drawable.gradient_blue
                "yellow" -> resultDrawable = R.drawable.gradient_yellow
                "green" -> resultDrawable = R.drawable.gradient_green
                "purple" -> resultDrawable = R.drawable.gradient_purple
            }


            return resultDrawable
        }

        fun transformColorLabelForText(colorLabel : String) : Int
        {
            var resultDrawable : Int = R.drawable.gradient_blue
            when(colorLabel)
            {
                "red"  -> resultDrawable = R.color.white
                "blue" -> resultDrawable =  R.color.white
                "yellow" -> resultDrawable = R.color.primaryColor
                "green" -> resultDrawable = R.color.white
                "purple" -> resultDrawable = R.color.white
            }


            return resultDrawable
        }

        fun addPrefixZero(param : Int) : String{
            var result: String = (if(param < 10) "0"+param.toString() else param.toString())
               return result

        }

        fun setCompletedTask(status : String) :String{
            val result : String
            when(status){
                "open" -> result = "completed"
                else -> result =  "open"
            }
            return result
        }

        fun setCheckedTask(status: String) :Boolean{
            var result = false
            if (status == "completed") result = true
            return result
        }

    }

}