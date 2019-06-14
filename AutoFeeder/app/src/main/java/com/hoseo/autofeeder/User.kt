package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

/*
    DB에 저장할 user 객체의 데이터 클래스
 */
@IgnoreExtraProperties
data class User(
    var userName: String? = "",
    var userDate: String? = "",
    var userWeight: String? = "",
    var userBirthday: String = "",
    var userBDay: String = ""
)
