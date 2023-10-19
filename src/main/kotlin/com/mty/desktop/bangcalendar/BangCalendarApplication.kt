package com.mty.desktop.bangcalendar

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.mty.desktop.bangcalendar.ui.App
import com.mty.desktop.bangcalendar.util.CalendarUtil
import java.awt.Dimension
import kotlin.math.roundToInt

object BangCalendarApplication {

    val systemDate = CalendarUtil() //系统时间

}

fun main() = application {
    Window(
        title = "BangCalendar Desktop",
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize(800.dp, 430.dp)),
        resizable = false
    ) {
        App()
    }
}
