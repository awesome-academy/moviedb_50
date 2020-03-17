package com.sun_asterisk.moviedb_50.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDate(strDate: String): String? {
        val formatStringToDate = SimpleDateFormat(Constant.BASE_STRING_TO_DATE, Locale.getDefault())
        val date = formatStringToDate.parse(strDate)
        val formatDateToString = SimpleDateFormat(Constant.BASE_DATE_TO_STRING, Locale.getDefault())
        return date?.let { formatDateToString.format(it) }
    }
}
