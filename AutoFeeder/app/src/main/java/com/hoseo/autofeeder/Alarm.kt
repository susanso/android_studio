package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

/*
    DB에 저장할 alarm 객체의 데이터 클래스
 */
@IgnoreExtraProperties
data class Alarm(
    var alarmHour : Int = 0,
    var alarmMinute : Int = 0
)
