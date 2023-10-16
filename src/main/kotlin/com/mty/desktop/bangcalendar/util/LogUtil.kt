package com.mty.desktop.bangcalendar.util

object LogUtil {

    private const val DEFAULT_TAG = "LogUtil"

    private const val ENABLED = true

    fun d(tag: String, msg: String) {
        if (ENABLED) {
            println("tag:$tag, msg:$msg")
        }
    }

    /**
     * 本函数将类名作为Log的Tag，只需传入msg即可
     * @param obj 一般情况下传入this即可，若this为匿名类，则采用LogUtil作为Tag
     */
    fun <T> d(obj: T, msg: String) {
        if (ENABLED) {
            val tag = obj!!::class.simpleName
            this.d(tag ?: DEFAULT_TAG, msg)
        }
    }

}