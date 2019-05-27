package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var dDayDataReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dDayDataReference = FirebaseDatabase.getInstance().reference.child("date").child("dDay")

        dDayDataReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dDay = dataSnapshot.getValue(String::class.java)

                if(!TextUtils.isEmpty(dDay)) {textDay.text = "D+${dDay}"}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDDay:onCancelled", databaseError.toException())
            }
        })

        picture.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

        feed.setOnClickListener{
            startActivity(Intent(this, FeedActivity::class.java))
        }
    }
}
