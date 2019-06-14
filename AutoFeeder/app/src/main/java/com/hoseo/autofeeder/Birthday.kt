package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

/*
    DB에 저장할 birthday 객체의 데이터 클래스

    bDay는 현재 날짜 - 태어난 날짜의 일 수
 */

@IgnoreExtraProperties
data class Birthday(
    var birthYear: Int? = 0,
    var birthMonth: Int? = 0,
    var birthDay: Int? = 0,
    var bDay: String = ""
)