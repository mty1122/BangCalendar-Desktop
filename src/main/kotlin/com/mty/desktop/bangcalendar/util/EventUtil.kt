package com.mty.desktop.bangcalendar.util

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import com.mty.desktop.bangcalendar.logic.model.Event
import com.mty.desktop.bangcalendar.logic.model.IntDate
import com.mty.desktop.bangcalendar.enum.EventConstant
import java.util.regex.Pattern

object EventUtil {

    private var eventLength: Long = 633600000

    fun getCharacterPainter(character: Int?) = character?.let {
        BitmapPainter(useResource("image/char_${it}.jpg", ::loadImageBitmap))
    }

    fun matchType(type: Int) =
        when (type) {
            EventConstant.NORMAL.id -> EventConstant.NORMAL.describe
            EventConstant.CHALLENGE.id -> EventConstant.CHALLENGE.describe
            EventConstant.FIGHT.id -> EventConstant.FIGHT.describe
            EventConstant.EX.id -> EventConstant.EX.describe
            EventConstant.MISSION.id -> EventConstant.MISSION.describe
            EventConstant.GROUP.id -> EventConstant.GROUP.describe
            EventConstant.MULTI.id -> EventConstant.MULTI.describe
            else -> "检索不到活动类型"
        }

    fun matchAttrs(attrs: Int) = attrs
    /*
        when (attrs) {
            EventConstant.PURE.id -> R.drawable.event_pure
            EventConstant.POWERFUL.id -> R.drawable.event_powerful
            EventConstant.COOL.id -> R.drawable.event_cool
            EventConstant.HAPPY.id -> R.drawable.event_happy
            else -> -1
        }
     */

    fun getAttrsName(attrs: Int) =
        when (attrs) {
            EventConstant.PURE.id -> EventConstant.PURE.describe
            EventConstant.POWERFUL.id -> EventConstant.POWERFUL.describe
            EventConstant.COOL.id -> EventConstant.COOL.describe
            EventConstant.HAPPY.id -> EventConstant.HAPPY.describe
            else -> -1
        }

    fun getBand(event: Event) =
        //角色1 + 2 = 角色5 - 2 若角色5不存在则为 角色1 + 2 = 角色3
        if (event.character1 + 2 == ((event.character5?.minus(2)) ?: event.character3))
            when (event.character1) {
                1 -> EventConstant.PPP
                6 -> EventConstant.AG
                11 -> EventConstant.PP
                16 -> EventConstant.R
                21 -> EventConstant.HHW
                26 -> EventConstant.M
                31 -> EventConstant.RAS
                else -> EventConstant.OTHER
            }
        else EventConstant.OTHER

    fun bandNameToCharacter1(bandName: String) =
        when (bandName) {
            EventConstant.PPP.describe -> 1
            EventConstant.AG.describe -> 6
            EventConstant.PP.describe -> 11
            EventConstant.R.describe -> 16
            EventConstant.HHW.describe -> 21
            EventConstant.M.describe -> 26
            EventConstant.RAS.describe -> 31
            else -> -1
        }

    fun getBandPic(event: Event) = 1
    /*
        when (event.character7) {
            null -> if (event.character1 + 2 ==
                ((event.character5?.minus(2)) ?: event.character3)) {
                when (event.character1) {
                    1 -> R.drawable.logo_ppp
                    6 -> R.drawable.logo_ag
                    11 -> R.drawable.logo_pp
                    16 -> R.drawable.logo_r
                    21 -> R.drawable.logo_hhw
                    26 -> R.drawable.logo_m
                    31 -> R.drawable.logo_ras
                    else -> R.drawable.logo_bang
                }
            } else { R.drawable.logo_bang }

            else -> R.drawable.logo_all
    }
     */

    fun eventIdFormat(id: Int) =
        when {
            id < 10 -> "00$id"
            id < 100 -> "0$id"
            else -> id.toString()
        }

    fun isSameEvent(eventCardTitle: String, eventId: Int): Boolean {
        val regex = "\\d+"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(eventCardTitle)
        if (matcher.find()) {
            return matcher.group() == eventId.toString()
        }
        return false
    }

    fun getEventStartTime(event: Event): Long =
        CalendarUtil(IntDate(event.startDate)).run {
        hour = 15
        getTimeInMillis()
        }

    fun getEventEndTime(event: Event): Long =
        CalendarUtil(IntDate(event.startDate)).run {
            day += 7
            hour = 23
            getTimeInMillis()
        }

    fun getEventEndTime(date: IntDate): Long =
        CalendarUtil(date).run {
            hour = 23
            getTimeInMillis()
        }

    fun getEventProgress(systemTime: Long, eventStartTime: Long): Int {
        val eventFinishedTimes = (systemTime - eventStartTime).toDouble()
        val eventProgress = ((eventFinishedTimes / eventLength) * 100).toInt()
        log(this, "eventProgress = $eventProgress")
        return eventProgress
    }

    fun setEventLength(eventLength: Long) {
        EventUtil.eventLength = eventLength
    }

}