package com.hoseo.autofeeder

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val timePicker = findViewById<TimePicker>(R.id.time_picker)

        set.setOnClickListener {
            var hour: Int
            val minute: Int
            val am_pm: String
            if (Build.VERSION.SDK_INT >= 23) {
                hour = timePicker.getHour()
                minute = timePicker.getMinute()
            } else {
                hour = timePicker.getCurrentHour()
                minute = timePicker.getCurrentMinute()
            }
            if (hour >= 12) {
                am_pm = "PM"
                if (hour != 12) {
                    hour = hour - 12
                }
            } else {
                am_pm = "AM"
            }
            Toast.makeText(
                this,
                "공급시간이 ${hour}시 ${minute}분 ${am_pm}으로 설정되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            onBackPressed()
        }

        reset.setOnClickListener {
            onBackPressed()
        }
    }

}

