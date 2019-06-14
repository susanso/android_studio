package com.hoseo.autofeeder


import android.app.*
import android.os.Bundle
import android.widget.TextView
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.Calendar


class BirthdayPickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    /*
        캘린더를 저장할 변수와 DB의 birthday 주소를 저장할 변수
     */

    private lateinit var calendar:Calendar
    private lateinit var birthdayReference: DatabaseReference

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        birthdayReference = FirebaseDatabase.getInstance().reference.child("birthday")

        val birthdayYear = calendar.get(Calendar.YEAR)
        val birthdayMonth = calendar.get(Calendar.MONTH)
        val birthdayDay = calendar.get(Calendar.DAY_OF_MONTH)

        /*
            **** reference source developer.android.com ***
            DatePicker를 Dialog로 표시
        */

        return DatePickerDialog(
            activity, // Context
            // Put 0 to system default theme or remove this parameter
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, // Theme
            this, // DatePickerDialog.OnDateSetListener
            birthdayYear, // Year
            birthdayMonth, // Month of year
            birthdayDay // Day of month
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

        activity.findViewById<TextView>(R.id.editBirthday).text = formatDate(year,month,day)

        birthdayReference.child("birthYear").setValue(year)
        birthdayReference.child("birthMonth").setValue(month)
        birthdayReference.child("birthDay").setValue(day)
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