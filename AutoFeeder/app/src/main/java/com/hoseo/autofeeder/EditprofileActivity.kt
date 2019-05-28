package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_editprofile.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class EditprofileActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var dateReference: DatabaseReference
    private lateinit var birthdayReference: DatabaseReference
    private lateinit var calendar:Calendar
    private lateinit var calendarChosen: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        database = FirebaseDatabase.getInstance().reference
        userReference = database.child("user")
        dateReference = database.child("date")
        birthdayReference = database.child("birthday")


        val userDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)

                val name = user?.userName
                val date = user?.userDate
                val weight = user?.userWeight
                val birthday = user?.userBirthday

                if(!TextUtils.isEmpty(name)) {editName.text = Editable.Factory.getInstance().newEditable(name)}
                if(!TextUtils.isEmpty(date)) {editDate.text = "${date}"}
                if(!TextUtils.isEmpty(weight)) {editWeight.text = Editable.Factory.getInstance().newEditable(weight)}
                if(!TextUtils.isEmpty(birthday)) {editBirthday.text = "${birthday}"}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadProfile:onCancelled", databaseError.toException())
                // ...
            }
        }

        val dateDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val date = dataSnapshot.getValue(Date::class.java)

                var year: Int
                var month: Int
                var day: Int

                year = date?.dateYear!!
                month = date?.dateMonth!!
                day = date?.dateDay!!

                calendar = Calendar.getInstance()
                val today = calendar.timeInMillis/(24*60*60*1000)

                calendarChosen = Calendar.getInstance()
                calendarChosen.set(year, month, day, 0, 0, 0)
                val chosenDay = calendarChosen.timeInMillis/(24*60*60*1000)

                val dDay = (today - chosenDay).toInt()
                textDDay.text = "$dDay"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDate:onCancelled", databaseError.toException())
            }
        }

        val birthdayDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val birthday = dataSnapshot.getValue(Birthday::class.java)

                var year: Int
                var month: Int
                var day: Int

                year = birthday?.birthYear!!
                month = birthday?.birthMonth!!
                day = birthday?.birthDay!!

                calendar = Calendar.getInstance()
                val today = calendar.timeInMillis/(24*60*60*1000)

                calendarChosen = Calendar.getInstance()
                calendarChosen.set(year, month, day, 0, 0, 0)
                val chosenDay = calendarChosen.timeInMillis/(24*60*60*1000)

                val bDay = (today - chosenDay).toInt()
                textBDay.text = "$bDay"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDate:onCancelled", databaseError.toException())
            }
        }

        userReference.addValueEventListener(userDataListener)
        dateReference.addValueEventListener(dateDataListener)
        birthdayReference.addValueEventListener(birthdayDataListener)

        editDateButton.setOnClickListener {
            val calendarFragment = DatePickerFragment()
            calendarFragment.show(fragmentManager, "EditDate")
        }

        editBirthdayButton.setOnClickListener {
            val calendarFragment = BirthdayPickerFragment()
            calendarFragment.show(fragmentManager, "EditBirthday")
        }

        cancelButton.setOnClickListener {
            onBackPressed()
        }

        applyButton.setOnClickListener {
            val name = editName.text.toString()
            val date = editDate.text.toString()
            val weight = editWeight.text.toString()
            val birthday = editBirthday.text.toString()
            val bDay = textBDay.text.toString()

            writeUserData(name, date, weight, birthday, bDay)

            val dDay = textDDay.text.toString()

            dateReference.child("dDay").setValue(dDay)
            birthdayReference.child("bDay").setValue(bDay)

            onBackPressed()
        }

    }

    private fun writeUserData(userName: String, userDate: String, userWeight: String, userBirthday: String, userBDay: String) {
        val user = User(userName, userDate, userWeight, userBirthday, userBDay)
        userReference.setValue(user)
    }
}
