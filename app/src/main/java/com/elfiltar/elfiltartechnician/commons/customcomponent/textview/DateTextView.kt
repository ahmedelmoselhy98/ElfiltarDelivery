package com.elfiltar.elfiltartechnician.commons.customcomponent.textview

import android.content.Context
import android.util.AttributeSet
import com.elfiltar.elfiltartechnician.business.general.sheets.CustomDatePickerSheet
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    var apiDate = ""
    var apiTime = ""
    var isValid = false

    init {
        setOnClickListener {
            CustomDatePickerSheet(context, onConfirm = {
                setDates(it)
            }).show()
        }
    }
    fun reset(){
        text = ""
        apiDate = ""
        apiTime = ""
        isValid = false
    }
    fun setDates(date: Date) {
        text = formatUiDate(date)
        apiDate = formatApiDate(date)
        apiTime = formatApiTime(date)
        isValid = !apiDate.isNullOrEmpty() && !apiDate.isNullOrEmpty()
    }

    private fun formatUiDate(date: Date): String {
        var dateStr = ""
        try {
            val formatter: DateFormat = SimpleDateFormat(
                "dd/MM/yyyy", Locale.ENGLISH
            ) //If you need time just put specific format for time like 'HH:mm:ss'
            dateStr = formatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateStr
    }

    private fun formatApiDate(date: Date): String {
        var dateStr = ""
        try {
            val formatter: DateFormat = SimpleDateFormat(
                "yyyy-MM-dd", Locale.ENGLISH
            ) //If you need time just put specific format for time like 'HH:mm:ss'
            dateStr = formatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateStr
    }

    private fun formatApiTime(date: Date): String {
        var dateStr = ""
        try {
            val formatter: DateFormat = SimpleDateFormat(
                "HH:mm", Locale.ENGLISH
            ) //If you need time just put specific format for time like 'HH:mm:ss'
            dateStr = formatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateStr
    }

}