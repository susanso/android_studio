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

    /*
        DB의 alarm 주소를 저장할 변수
     */

    private lateinit var alarmReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarmReference = FirebaseDatabase.getInstance().reference.child("alarm")

        set.setOnClickListener {
            var hour: Int
            val minute: Int
            /*
                SDK 버전에 따라 다른 메소드를 사용
             */
            if (Build.VERSION.SDK_INT >= 23) {
                hour = time_picker.getHour()
                minute = time_picker.getMinute()
            } else {
                hour = time_picker.getCurrentHour()
                minute = time_picker.getCurrentMinute()
            }
            Toast.makeText(
                this,
                "공급시간이 ${hour}시 ${minute}분으로 설정되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            writeAlarmData(hour, minute)

            /*
                DB에 저장한 후 이전 화면으로 돌아감
             */

            onBackPressed()
        }

        reset.setOnClickListener {
            onBackPressed()
        }

    }

    /*
        설정된 시, 분을 DB에 저장하는 메소드
    */

    private fun writeAlarmData(alarmHour : Int, alarmMinute : Int) {
        val alarm = Alarm(alarmHour, alarmMinute)
        alarmReference.push().setValue(alarm)
    }

}

