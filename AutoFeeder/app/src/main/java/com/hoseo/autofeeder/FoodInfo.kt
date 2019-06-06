package com.hoseo.autofeeder

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FoodInfo(
    var foodName: String = "",
    var foodDesc: String = "",
    var foodImageURL: String = ""
)