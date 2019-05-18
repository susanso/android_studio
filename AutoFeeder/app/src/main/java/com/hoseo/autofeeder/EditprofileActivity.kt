package com.hoseo.autofeeder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editprofile.*

class EditprofileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        editDateButton.setOnClickListener {
            val calendarFragment = DatePickerFragment()
            calendarFragment.show(fragmentManager, "EditDate")
        }

        editBirthdayButton.setOnClickListener {
            val calendarFragment = BirthdayPickerFragment()
            calendarFragment.show(fragmentManager, "EditBirthday")
        }
    }
}
