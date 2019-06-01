package com.hoseo.autofeeder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_log.*
import com.google.firebase.database.DataSnapshot



class LogActivity : AppCompatActivity() {
    private lateinit var alarmReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        alarmReference = FirebaseDatabase.getInstance().reference.child("alarm")

        var logs: MutableList<Alarm> = mutableListOf()

        alarmReference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val alarm = p0.getValue(Alarm::class.java)
                val hour = alarm!!.alarmHour
                val minute = alarm!!.alarmMinute

                logs.add(Alarm(hour, minute))

                logList.adapter = LogAdapter(logs)
                logList.layoutManager = LinearLayoutManager(this@LogActivity)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val alarm = p0.getValue(Alarm::class.java)
                val hour = alarm!!.alarmHour
                val minute = alarm!!.alarmMinute

                logs.add(Alarm(hour, minute))

                logList.adapter = LogAdapter(logs)
                logList.layoutManager = LinearLayoutManager(this@LogActivity)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }


        })
    }
}