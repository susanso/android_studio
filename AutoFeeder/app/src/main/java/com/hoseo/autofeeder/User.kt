package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var userName: String? = "",
    var userDate: String? = "",
    var userWeight: String? = "",
    var userBirthday: String = "",
    var userBDay: String = ""
)
