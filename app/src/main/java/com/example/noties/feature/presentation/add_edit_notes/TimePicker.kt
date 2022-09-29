package com.example.noties.feature.presentation.add_edit_notes

import android.app.TimePickerDialog
import android.content.Context
import java.util.*


fun TimePicker(
    context: Context,
    onTimePick: (Calendar) -> Unit
) {

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    TimePickerDialog(
        context,
        { _, mHour: Int, mMinute: Int ->
            mCalendar.set(Calendar.HOUR_OF_DAY,mHour)
            mCalendar.set(Calendar.MINUTE,mMinute)
            onTimePick(mCalendar)
        },
        mHour, mMinute,
        true
    ).show()

}