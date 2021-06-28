package com.bowyer.app.todoapp.ext

import com.bowyer.app.todoapp.domain.DateTimeVO
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

private const val YYYY_MM_DD_FORMAT = "yyyy/MM/dd"

fun DateTimeVO.yyyyMMdd(): String {
  val sdf = SimpleDateFormat(YYYY_MM_DD_FORMAT, Locale.JAPAN)
  val date = try {
    sdf.parse(DateTime(value).toString())
  } catch (e: Throwable) {
    Date()
  }
  return sdf.format(date).toString()
}

fun DateTimeVO.yyyyMMddEEFromUnixTime(): String {
  val tokyo = DateTimeZone.forID("Asia/Tokyo")
  val format = DateTimeFormat.forPattern("MM/dd（EEE）")
  // UNIX TIMEは1000倍する必要があるみたい
  return format.print(DateTime(value * 1000L, tokyo))
}
