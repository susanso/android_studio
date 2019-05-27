package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    private lateinit var userReference: DatabaseReference
    private lateinit var dateReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userReference = FirebaseDatabase.getInstance().reference.child("user")
        dateReference = FirebaseDatabase.getInstance().reference.child("date")

        val userDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)

                val name = user?.userName
                val date = user?.userDate
                val weight = user?.userWeight
                val birthday = user?.userBirthday

                if(!TextUtils.isEmpty(name)) {textName.text = "${name}"}
                if(!TextUtils.isEmpty(date)) {textDate.text = "${date}"}
                if(!TextUtils.isEmpty(weight)) {textWeight.text = "${weight}g"}
                if(!TextUtils.isEmpty(birthday)) {textBirthday.text = "${birthday}"}
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

                val dDay = date?.dDay
                if(!TextUtils.isEmpty(dDay)) {textDay.text = "+${dDay}일"}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDate:onCancelled", databaseError.toException())
            }
        }

        userReference.addValueEventListener(userDataListener)
        dateReference.addValueEventListener(dateDataListener)

        profileEdit.setOnClickListener {
            startActivity(Intent(this, EditprofileActivity::class.java))
        }
    }
}
