package au.com.emerg.taxitowncars.listeners

import org.joda.time.LocalDateTime

interface IDateTimeListener {
    fun onDateTimeSet(dateTime: LocalDateTime)
}