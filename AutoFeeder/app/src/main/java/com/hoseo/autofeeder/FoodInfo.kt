package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

/*
    DB에 저장할 foodInfo 객체의 데이터 클래스
 */
@IgnoreExtraProperties
data class FoodInfo(
    var foodName: String = "",
    var foodDesc: String = "",
    var foodImageURL: String = ""
)