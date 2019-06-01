package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Alarm(
    var alarmHour : Int = 0,
    var alarmMinute : Int = 0
)
