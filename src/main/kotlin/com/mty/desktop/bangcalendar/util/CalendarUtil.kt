package com.mty.desktop.bangcalendar.util

import com.mty.desktop.bangcalendar.logic.model.IntDate
import java.util.Calendar

class CalendarUtil(date: IntDate? = null) {

    operator fun minus(calendarUtil: CalendarUtil): Int =
        ( (this.getTimeInMillis() - calendarUtil.getTimeInMillis()) / (1000 * 3600 * 24) ).toInt()

    companion object {

        const val FIVE_ROWS = 5
        const val SIX_ROWS = 6

        fun getDate(year: Int, month: Int, day: Int) =
            IntDate(year * 10000 + month * 100 + day)

    }

    private val calendar = Calendar.getInstance()

    var rows: Int = FIVE_ROWS

    var year: Int
        get() = calendar.get(Calendar.YEAR)
        set(value) = calendar.set(Calendar.YEAR, value)

    //月默认从0开始
    var month: Int
        get() = calendar.get(Calendar.MONTH) + 1
        set(value) = calendar.set(Calendar.MONTH, value - 1)

    var day: Int
        get() = calendar.get(Calendar.DATE)
        set(value) = calendar.set(Calendar.DATE, value)

    var hour: Int
        get() = calendar.get(Calendar.HOUR_OF_DAY)
        set(value) = calendar.set(Calendar.HOUR_OF_DAY, value)

    init {
        //采用date初始化CalendarUtil(有参)
        date?.let {
            clear()
            year = it.value / 10000
            month = it.value % 10000 / 100
            day = it.value % 100
        }
        calendar.firstDayOfWeek = Calendar.SUNDAY
        refreshRows()
    }

    fun getMaximumDaysInMonth(): Int {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getDayOfWeak(): Int {
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun setRelativeMonth(month: Int) {
        calendar.add(Calendar.MONTH, month)
        refreshRows()
    }

    fun clearDays() {
        calendar.set(Calendar.DATE, 1)
    }

    fun clear() {
        calendar.clear()
    }

    fun getTimeInMillis() = calendar.timeInMillis

    fun getDaysList(): List<String> {
        val maxDays = getMaximumDaysInMonth()
        val day = this.day
        this.day = 1
        val dayOfWeak = getDayOfWeak()
        this.day = day
        val dateList = ArrayList<String>()
        repeat(dayOfWeak - 1) {
            dateList.add("")
        }
        for (date in 1 until maxDays + 1) {
            dateList.add(date.toString())
        }
        return dateList
    }

    private fun refreshRows() {
        val maxDays = getMaximumDaysInMonth()
        val day = this.day
        this.day = 1
        val dayOfWeak = getDayOfWeak()
        this.day = day
        rows = if (maxDays + dayOfWeak < 37) {
            FIVE_ROWS
        } else {
            SIX_ROWS
        }
        log(this, "Rows is $rows")
    }

    fun toDate() = getDate(year, month, day)

    fun getTimeName(): String = when (calendar.get(Calendar.HOUR_OF_DAY)) {
        in 0..5 , in 19..24 -> "晚上"
        in 6..10 -> "上午"
        in 11..12 -> "中午"
        in 13..18 -> "下午"
        else -> ""
    }

    fun isSameDate(calendarUtil: CalendarUtil) = this.year == calendarUtil.year
            && this.month == calendarUtil.month && this.day == calendarUtil.day

    override fun toString() = "${year}年${month}月${day}日"

}
