package com.mty.desktop.bangcalendar.ui

import com.mty.desktop.bangcalendar.util.CalendarUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ViewModel {

    private val _calendarViewState = MutableStateFlow(CalendarUtil())
    val calendarViewState: StateFlow<CalendarUtil>
        get() = _calendarViewState
    private fun setCalendarViewState(calendarUtil: CalendarUtil) {
        _calendarViewState.value = calendarUtil
        _currentDay.value = calendarUtil.day
    }

    private val _currentDay = MutableStateFlow(calendarViewState.value.day)
    val currentDay: StateFlow<Int>
        get() = _currentDay
    fun setCurrentDay(day: Int) {
        _calendarViewState.value.day = day
        _currentDay.value = day
    }

    fun setRelativeCurrentMonth(month: Int) {
         setCalendarViewState(
             CalendarUtil(_calendarViewState.value.toDate()).apply { setRelativeMonth(month) }
         )
    }

    fun jumpToToday() {
        setCalendarViewState(CalendarUtil())
    }

}