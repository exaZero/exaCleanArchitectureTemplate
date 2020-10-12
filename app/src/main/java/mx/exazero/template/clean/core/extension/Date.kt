package mx.exazero.template.clean.core.extension

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

fun Date.isAtLeastXYearsOld(minAge: Int): Boolean{
    //Calendar.getInstance().apply { time = this@isAtLeastXYearsOld }.get(Calendar.YEAR)
    //return (Calendar.getInstance().get(Calendar.YEAR) - Calendar.getInstance().apply { time = this@isAtLeastXYearsOld }.get(Calendar.YEAR)) >= minAge
    return (Date().year - this.year) >= minAge
}

fun Date.toAge(): Int{
    return (Date().year - this.year)
}

fun Date.getDefaultAgeDate(): Date {
    return Calendar.getInstance().apply {
        set(this.get(Calendar.YEAR),0,1)
    }.time
}

fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().apply { time = this@toCalendar }
}

fun Date.withFormat(format : String) : String{
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this)
}

fun Date.withChatRelativeFormat(format: String) : String{
    if(DateUtils.isToday(this.time) || DateUtils.isToday(this.time + DateUtils.DAY_IN_MILLIS)){
        return DateUtils.getRelativeTimeSpanString(this.time, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString()
    }
    else{
        return this.withFormat(format)
    }
}

fun Date.withLastSeenRelativeFormat(format: String) : String{
    if(DateUtils.isToday(this.time) || DateUtils.isToday(this.time + DateUtils.DAY_IN_MILLIS)){
        return DateUtils.getRelativeTimeSpanString(this.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString()
    }
    else{
        return this.withFormat(format)
    }
}