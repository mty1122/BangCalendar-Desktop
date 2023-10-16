package com.mty.desktop.bangcalendar.util

fun toast(text: String) {
    println("toast:$text")
}

/**
 * 采用类名作为tag发出debug log
 * @param obj 一般情况下传入this即可，若this为匿名类，则采用LogUtil作为Tag
 */
fun <T> log(obj: T, msg: String) {
    LogUtil.d(obj, msg)
}