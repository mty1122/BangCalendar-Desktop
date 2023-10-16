package com.mty.desktop.bangcalendar

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mty.desktop.bangcalendar.ui.App
import com.mty.desktop.bangcalendar.util.CalendarUtil

object BangCalendarApplication {

    val systemDate = CalendarUtil() //系统时间

}

fun main() = application {
    Window(
        title = "BangCalendar Desktop",
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
