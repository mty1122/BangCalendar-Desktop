package com.mty.desktop.bangcalendar

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.mty.desktop.bangcalendar.ui.App
import com.mty.desktop.bangcalendar.util.CalendarUtil
import com.mty.desktop.bangcalendar.util.CharacterUtil

object BangCalendarApplication {

    val systemDate = CalendarUtil() //系统时间
    val characterList = CharacterUtil.getCharacterList()

}

fun main() = application {
    Window(
        title = "BangCalendar Desktop",
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize(800.dp, 500.dp)),
        resizable = false
    ) {
        App()
    }
}
