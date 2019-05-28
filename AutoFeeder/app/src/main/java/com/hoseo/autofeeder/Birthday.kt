package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Birthday(
    var birthYear: Int? = 0,
    var birthMonth: Int? = 0,
    var birthDay: Int? = 0,
    var bDay: String = ""
)