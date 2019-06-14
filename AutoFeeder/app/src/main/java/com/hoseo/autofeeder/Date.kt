package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

/*
    DB에 저장할 Date 객체의 데이터 클래스
 */

@IgnoreExtraProperties
data class Date(
    var dateYear: Int? = 0,
    var dateMonth: Int? = 0,
    var dateDay: Int? = 0,
    var dDay: String? = ""
)