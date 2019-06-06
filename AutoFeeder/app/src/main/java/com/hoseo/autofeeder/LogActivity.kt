package com.hoseo.autofeeder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_log.*
import com.google.firebase.database.DataSnapshot
import java.sql.Timestamp


class LogActivity : AppCompatActivity() {
    private lateinit var logReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        logReference = FirebaseDatabase.getInstance().reference.child("log")

        var logsDate: MutableList<Long> = mutableListOf()

        logReference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val log = p0.getValue(Long::class.java)

                if (log != null) {
                    logsDate.add(log)
                }

                logList.adapter = LogAdapter(logsDate)
                logList.layoutManager = LinearLayoutManager(this@LogActivity)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val log = p0.getValue(Long::class.java)

                if (log != null) {
                    logsDate.add(log)
                }

                logList.adapter = LogAdapter(logsDate)
                logList.layoutManager = LinearLayoutManager(this@LogActivity)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }


        })
    }
}