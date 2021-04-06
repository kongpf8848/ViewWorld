package com.github.kongpf8848.viewworld.utis

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    val YY_MM_DD_HH_MM_SS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val YY_MM_DD_HH_MM_SS_SSS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")

    fun formatDate(ms: Long): String {
        return formatDate(Date(ms))
    }

    @JvmOverloads
    fun formatDate(date: Date? = Date()): String {
        return formatDate(
            YY_MM_DD_HH_MM_SS,
            date
        )
    }

    fun formatDate(format: String?, date: Date?): String {
        return formatDate(
            SimpleDateFormat(
                format
            ), date
        )
    }

    fun formatDate(format: String?, ms: Long): String {
        return formatDate(
            SimpleDateFormat(
                format
            ), ms
        )
    }

    //格式化日期
    fun formatDate(dateFormat: SimpleDateFormat, date: Date?): String {
        try {
            return dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun formatDate(dateFormat: SimpleDateFormat, ms: Long): String {
        try {
            return dateFormat.format(ms)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}