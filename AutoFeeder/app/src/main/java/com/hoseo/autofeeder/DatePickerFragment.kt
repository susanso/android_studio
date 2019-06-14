package com.hoseo.autofeeder


import android.app.*
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.DateFormat
import java.util.Calendar


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    /*
        캘린더를 저장할 변수와 DB의 date 주소를 저장할 변수
     */

    private lateinit var calendar:Calendar
    private lateinit var dateReference: DatabaseReference

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        dateReference = FirebaseDatabase.getInstance().reference.child("date")

        val dateYear = calendar.get(Calendar.YEAR)
        val dateMonth = calendar.get(Calendar.MONTH)
        val dateDay = calendar.get(Calendar.DAY_OF_MONTH)

        /*
            **** reference source developer.android.com ***
            DatePicker를 Dialog로 표시
        */

        return DatePickerDialog(
            activity, // Context
            // Put 0 to system default theme or remove this parameter
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, // Theme
            this, // DatePickerDialog.OnDateSetListener
            dateYear, // Year
            dateMonth, // Month of year
            dateDay // Day of month
        )
    }

    /*
      날짜를 고르고 적용을 눌렀을 때 실행되는 메소드
      DB에 설정된 년, 월, 일을 저장하고 TextView에 표시
    */

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        Toast.makeText(
            activity,
            "${formatDate(year,month,day)} 로 변경되었습니다."
            ,Toast.LENGTH_SHORT
        ).show()

        activity.findViewById<TextView>(R.id.editDate).text = formatDate(year,month,day)

        dateReference.child("dateYear").setValue(year)
        dateReference.child("dateMonth").setValue(month)
        dateReference.child("dateDay").setValue(day)
    }

    /*
       날짜의 표시 형식을 결정하기위한 메소드
    */

    private fun formatDate(year:Int, month:Int, day:Int):String{
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }

}