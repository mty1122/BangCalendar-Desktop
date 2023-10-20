package com.mty.desktop.bangcalendar.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mty.desktop.bangcalendar.BangCalendarApplication.systemDate
import com.mty.desktop.bangcalendar.util.CalendarUtil

object MainView {

    @Composable
    fun CalendarMonthTitle() {
        val calendarViewState by ViewModel.calendarViewState.collectAsState()
        Text(
            text = "${calendarViewState.year}年${calendarViewState.month}月",
            modifier = Modifier.padding(20.dp, 10.dp)
        )
    }

    @Composable
    fun CalendarMonthView(birthdayMap: HashMap<String, Int>) {
        val currentDay by ViewModel.currentDay.collectAsState()
        val calendarViewState by ViewModel.calendarViewState.collectAsState()
        val currentDaysList = calendarViewState.getDaysList()
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = when (calendarViewState.rows) {
                CalendarUtil.FIVE_ROWS -> Arrangement.spacedBy(16.dp)
                else -> Arrangement.spacedBy(5.dp)
            },
            contentPadding = PaddingValues(5.dp),
            columns = GridCells.Fixed(7)
        ) {
            val daysOfWeek = listOf("日", "一", "二", "三", "四", "五", "六")
            items(daysOfWeek.size) { index ->
                Text(
                    text = daysOfWeek[index],
                    textAlign = TextAlign.Center
                )
            }
            items(currentDaysList.size) { index ->
                if (currentDaysList[index] != ""
                    && currentDay == currentDaysList[index].toInt()) {
                    CalendarDateItem(
                        day = currentDaysList[index],
                        isSelected = true,
                        isSystemDate = calendarViewState.isSameDate(systemDate)
                    ){}
                } else {
                    CalendarDateItem(
                        day = currentDaysList[index],
                        isSelected = false,
                        isSystemDate = false
                    ){ ViewModel.setCurrentDay(currentDaysList[index].toInt()) }
                }
            }

        }
    }

    @Composable
    fun CalendarDateItem(
        day: String,
        isSelected: Boolean,
        isSystemDate: Boolean,
        characterImage: Painter? = null,
        onClick: () -> Unit
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .background(
                        color = when {
                        isSystemDate -> Color.Blue
                        else -> Color.LightGray
                    },
                        shape = CircleShape
                    )
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) { Text(day) }
        } else {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                if (characterImage != null)
                    Image(characterImage, "角色图片")
                else
                    Text(day)
            }
        }
    }

    @Composable
    fun ControlButtons() {
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            Button(
                modifier = Modifier.padding(start = 15.dp),
                onClick = {
                    ViewModel.setRelativeCurrentMonth(-1)
                }
            ){ Text("上个月") }
            Button(
                onClick = {
                    ViewModel.jumpToToday()
                }
            ){ Text("返回今天") }
            Button(
                onClick = {
                    ViewModel.setRelativeCurrentMonth(1)
                }
            ){ Text("下个月") }
        }
    }

}

@Composable
fun App() {
    MaterialTheme {
        Row {
            //主界面左侧
            Column(
                modifier = Modifier.weight(1f)
            ) {
                MainView.CalendarMonthTitle()
                MainView.CalendarMonthView(HashMap())
                MainView.ControlButtons()
            }
            //主界面右侧
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("${systemDate.getTimeName()}好！")
            }
        }
    }
}

@Deprecated("compose-jb暂不支持pager翻页")
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CalendarViewFuture(calendarUtil: CalendarUtil) {
    val pagerState = rememberPagerState(5)
    HorizontalPager(
        pageCount = 10,
        state =  pagerState,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(it.toString(), modifier = Modifier.size(200.dp).background(Color.Blue))
    }
}
