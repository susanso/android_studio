package com.hoseo.autofeeder

import android.content.Intent
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

        cancelButton.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

        applyButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)

            val name = editName.text.toString()
            val date = editDate.text.toString()
            val birthday = editBirthday.text.toString()
            val weight = editWeight.text.toString()

            intent.putExtra("name", name)
            intent.putExtra("date", date)
            intent.putExtra("birthday", birthday)
            intent.putExtra("weight", weight)

            startActivity(intent)
        }

    }
}
