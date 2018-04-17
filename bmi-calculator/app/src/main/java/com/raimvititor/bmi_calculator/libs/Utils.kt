package com.raimvititor.bmi_calculator.libs

import android.widget.EditText
import java.util.*
import java.util.Calendar.*

class Utils {
    companion object {
        fun setEditTextNotEdit(editText: EditText) {
            editText.isFocusableInTouchMode = false
            editText.isFocusable = false
            editText.isClickable = true
        }

        fun getOld(first: Date): Int {
            return getDiffYears(first, Date())
        }

        private fun getDiffYears(first: Date, last: Date): Int {
            val a = getCalendar(first)
            val b = getCalendar(last)
            var diff = b.get(YEAR) - a.get(YEAR)
            if (a[MONTH] > b[MONTH] || a[MONTH] == b[MONTH] && a[DATE] > b[DATE]) {
                diff--
            }
            return diff
        }

        private fun getCalendar(date: Date): Calendar {
            val cal = Calendar.getInstance(Locale.US)
            cal.time = date
            return cal
        }
    }
}