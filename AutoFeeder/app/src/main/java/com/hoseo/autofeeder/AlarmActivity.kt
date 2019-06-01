package com.hoseo.autofeeder

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var alarmReference: DatabaseReference
    private lateinit var alarmTextReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarmReference = FirebaseDatabase.getInstance().reference.child("alarm")
        alarmTextReference = FirebaseDatabase.getInstance().reference.child("alarmText")

        val timePicker = findViewById<TimePicker>(R.id.time_picker)

        set.setOnClickListener {
            var hour: Int
            val minute: Int
            if (Build.VERSION.SDK_INT >= 23) {
                hour = timePicker.getHour()
                minute = timePicker.getMinute()
            } else {
                hour = timePicker.getCurrentHour()
                minute = timePicker.getCurrentMinute()
            }
            Toast.makeText(
                this,
                "공급시간이 ${hour}시 ${minute}분으로 설정되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            writeAlarmData(hour, minute)

            onBackPressed()
        }

        reset.setOnClickListener {
            onBackPressed()
        }

    }

    private fun writeAlarmData(alarmHour : Int, alarmMinute : Int) {
        val alarm = Alarm(alarmHour, alarmMinute)
        alarmReference.push().setValue(alarm)
    }

}

