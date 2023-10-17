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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mty.desktop.bangcalendar.util.CalendarUtil

object MainView {

    @Composable
    fun CalendarMonthView(calendarUtil: CalendarUtil, birthdayMap: HashMap<String, Int>) {
        LazyVerticalGrid(GridCells.Fixed(7)) {
            val dateList = calendarUtil.getDateList()
            val daysOfWeek = listOf("日", "一", "二", "三", "四", "五", "六")
            items(daysOfWeek.size) { index ->
                Text(
                    text = daysOfWeek[index],
                    textAlign = TextAlign.Center
                )
            }
            items(dateList.size) { index ->
                CalendarDateItem(dateList[index], false, false){}
            }
        }
    }

    @Composable
    fun CalendarDateItem(
        date: String,
        isSelected: Boolean,
        isSystemDate: Boolean,
        characterImage: Painter? = null,
        onClick: () -> Unit
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .background(color = when {
                        isSystemDate -> Color.Blue
                        else -> Color.LightGray
                    })
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier.clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                if (characterImage != null)
                    Image(characterImage, null)
                else
                    Text(date)
            }
        }
    }

}

@Composable
fun App() {
    MaterialTheme {
        MainView.CalendarMonthView(CalendarUtil().apply { clearDays() }, HashMap())
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
