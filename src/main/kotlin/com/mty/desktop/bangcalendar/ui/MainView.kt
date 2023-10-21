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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mty.desktop.bangcalendar.BangCalendarApplication.systemDate
import com.mty.desktop.bangcalendar.theme.BangCalendarTheme
import com.mty.desktop.bangcalendar.theme.light_gray
import com.mty.desktop.bangcalendar.util.CalendarUtil
import com.mty.desktop.bangcalendar.util.CharacterUtil
import com.mty.desktop.bangcalendar.util.EventUtil

object MainView {

    @Composable
    fun CalendarMonthTitle() {
        val calendarViewState by ViewModel.calendarViewState.collectAsState()
        Text(
            text = "${calendarViewState.year}年${calendarViewState.month}月",
            modifier = Modifier
                .height(45.dp)
                .padding(20.dp, 10.dp)
                .fillMaxSize(),
            fontSize = 21.sp,
            color = Color.White
        )
    }

    @Composable
    fun MainViewLogo() {
        Image(
            painter = painterResource("image/logo.png"),
            contentDescription = "logo",
            alignment = Alignment.CenterEnd,
            modifier = Modifier
                .height(45.dp)
                .padding(20.dp, 10.dp)
                .fillMaxSize()
        )
    }

    @Composable
    fun CalendarWeekBar() {
        val daysOfWeek = listOf("日", "一", "二", "三", "四", "五", "六")
        Row(
            modifier = Modifier
                .padding(5.dp)
                .height(22.dp)
        ) {
            for (day in daysOfWeek) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    text = day,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun WelcomeBar() {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .height(22.dp),
            text = "${systemDate.getTimeName()}好，邦邦人！欢迎使用BangCalendar Desktop~",
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            color = Color.White
        )
    }

    @Composable
    fun CalendarMonthView() {
        val currentDay by ViewModel.currentDay.collectAsState()
        val calendarViewState by ViewModel.calendarViewState.collectAsState()
        val currentDaysList = calendarViewState.getDaysList()
        val currentBirthdayMap = CharacterUtil.run {
            characterListToBirthdayMap(getCharacterListByMonth(calendarViewState.month))
        }
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = when (calendarViewState.rows) {
                CalendarUtil.FIVE_ROWS -> Arrangement.spacedBy(17.dp)
                else -> Arrangement.spacedBy(3.dp)
            },
            contentPadding = PaddingValues(5.dp),
            columns = GridCells.Fixed(7)
        ) {
            items(currentDaysList.size) { index ->
                //选中状态
                if (currentDaysList[index] != ""
                    && currentDay == currentDaysList[index].toInt()) {
                    CalendarDateItem(
                        day = currentDaysList[index],
                        isSelected = true,
                        isSystemDate = calendarViewState.isSameDate(systemDate)
                    ){}
                //非选中状态
                } else {
                    CalendarDateItem(
                        day = currentDaysList[index],
                        isSelected = false,
                        isSystemDate = false,
                        characterImage = EventUtil
                            .getCharacterPainter(currentBirthdayMap[currentDaysList[index]])
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
                        isSystemDate -> MaterialTheme.colors.primary
                        else -> light_gray
                    },
                        shape = CircleShape
                    )
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    color = when {
                        isSystemDate -> Color.White
                        else -> MaterialTheme.colors.primary
                    },
                    text = day
                )
            }
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
                    Text(
                        color = MaterialTheme.colors.onBackground,
                        text = day
                    )
            }
        }
    }

    @Composable
    fun ControlButtons() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(
                        start = 15.dp,
                        bottom = 13.dp),
                onClick = {
                    ViewModel.setRelativeCurrentMonth(-1)
                }
            ){ Text("上个月") }
            Button(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(bottom = 13.dp),
                onClick = {
                    ViewModel.jumpToToday()
                }
            ){ Text("返回今天") }
            Button(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(
                        end = 15.dp,
                        bottom = 13.dp),
                onClick = {
                    ViewModel.setRelativeCurrentMonth(1)
                }
            ){ Text("下个月") }
        }
    }

    //年月周栏
    @Composable
    fun BarGroup() {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant)
        ) {
            CalendarMonthTitle()
            CalendarWeekBar()
        }
    }

    //日历部分
    @Composable
    fun CalendarViewGroup() {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            CalendarMonthView()
            ControlButtons()
        }
    }

    //顶部logo欢迎区域
    @Composable
    fun WelcomeGroup() {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .height(77.dp)
        ) {
            MainViewLogo()
            WelcomeBar()
        }
    }

    //功能卡片组
    @Composable
    fun CardGroup() {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "功能卡片开发中",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }

}

@Composable
fun App() {
    BangCalendarTheme {
        Row {
            //主界面左侧
            Column(
                modifier = Modifier.weight(1f)
            ) {
                MainView.BarGroup()
                MainView.CalendarViewGroup()
            }
            //主界面右侧
            Column(
                modifier = Modifier.weight(1f),
            ) {
                MainView.WelcomeGroup()
                MainView.CardGroup()
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
