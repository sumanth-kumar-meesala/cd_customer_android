package au.com.emerg.taxitowncars.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import au.com.emerg.taxitowncars.listeners.IDateTimeListener
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*


class DateTimeUtil {
    companion object {

        val MMM_DD_YY_HH_MM = "MMM dd, yyyy hh:mm aa"
        val DD_MM_YY_HH_MM = "dd-MM-yy HH:mm"

        fun selectDate(context: Context, iDateTimeListener: IDateTimeListener) {
            selectDate(null, context, iDateTimeListener)
        }

        fun selectDate(dateTime: Date?, context: Context, iDateTimeListener: IDateTimeListener) {
            val calendar = Calendar.getInstance()

            if (dateTime != null) {
                calendar.timeInMillis = dateTime.time
            }

            val cYear = calendar.get(Calendar.YEAR)
            val cMonth = calendar.get(Calendar.MONTH)
            val cDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    val date = "$day-$month-$year"
                    showTimePicker(dateTime, context, date, iDateTimeListener)
                },
                cYear,
                cMonth,
                cDay
            )
            datePickerDialog.show()
        }

        private fun showTimePicker(
            dateTime: Date?,
            context: Context,
            date: String,
            iDateTimeListener: IDateTimeListener
        ) {
            val calendar = Calendar.getInstance()

            if (dateTime != null) {
                calendar.timeInMillis = dateTime.time
            }

            val mHour = calendar.get(Calendar.HOUR_OF_DAY)
            val mMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    val value = "$date $hourOfDay:$minute"

                    val formattedDateTime = formatStringToDate(value)

                    iDateTimeListener.onDateTimeSet(formattedDateTime)

                }, mHour, mMinute, false
            )
            timePickerDialog.show()
        }

        fun formatStringToDate(dateTime: String): LocalDateTime {
            val format = DateTimeFormat.forPattern(DD_MM_YY_HH_MM)
            return LocalDateTime.parse(dateTime, format)
        }

        fun formatDateToString(dateTime: LocalDateTime): String {
            val format = SimpleDateFormat(MMM_DD_YY_HH_MM, Locale.getDefault())
            return format.format(dateTime.toDate())
        }
    }
}