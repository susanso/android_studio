package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Date(
    var dateYear: Int? = 0,
    var dateMonth: Int? = 0,
    var dateDay: Int? = 0,
    var dDay: String? = ""
)