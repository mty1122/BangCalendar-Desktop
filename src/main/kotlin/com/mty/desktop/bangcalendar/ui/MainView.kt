package com.mty.desktop.bangcalendar.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import com.mty.desktop.bangcalendar.BangCalendarApplication
import com.mty.desktop.bangcalendar.util.CalendarUtil

object MainView {

    @Composable
    fun CalendarMonthView(calendarUtil: CalendarUtil, birthdayMap: HashMap<String, Int>) {

    }

    @Composable
    fun CalendarDateItem(day: String, isSelected: Boolean, image: Painter? = null) {

    }

}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("${BangCalendarApplication.systemDate.getTimeName()}好！点击按钮查看日期") }

    MaterialTheme {
        Button(onClick = {
            text = "今天的日期是 ${BangCalendarApplication.systemDate}"
        }) {
            Text(text)
        }
    }
}
