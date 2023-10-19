package com.mty.desktop.bangcalendar.ui

import com.mty.desktop.bangcalendar.BangCalendarApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ViewModel {

    private val _currentDay = MutableStateFlow(BangCalendarApplication.systemDate.day)
    val currentDay: StateFlow<Int>
        get() = _currentDay
    fun setCurrentDay(day: Int) {
        _currentDay.value = day
    }

}