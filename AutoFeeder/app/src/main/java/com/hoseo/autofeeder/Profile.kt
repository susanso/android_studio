package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.math.round
import kotlin.math.roundToInt

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
                val bDay = user?.userBDay

                if(!TextUtils.isEmpty(name)) {textName.text = "${name}"}
                if(!TextUtils.isEmpty(date)) {textDate.text = "${date}"}
                if(!TextUtils.isEmpty(weight)) {textWeight.text = "${weight}g"}
                if(!TextUtils.isEmpty(birthday)) {textBirthday.text = "${birthday}"}

                val bDayInt = bDay!!.toInt()
                val weightDouble = weight!!.toDouble()

                val amountRecommended = when(bDayInt) {
                    in 0..60 -> (weightDouble*0.07).roundToInt()
                    in 60..90 -> (weightDouble*0.06).roundToInt()
                    in 90..150 -> (weightDouble*0.05).roundToInt()
                    in 150..365 -> (weightDouble*0.03).roundToInt()
                    in 365..1825 -> (weightDouble*0.025).roundToInt()
                    else -> (weightDouble*0.02).roundToInt()
                }

                textAmount.text = "${amountRecommended}g"
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
