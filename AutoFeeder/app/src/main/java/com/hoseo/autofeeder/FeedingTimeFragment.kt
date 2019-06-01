package com.hoseo.autofeeder


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedingTimeFragment : Fragment() {

    private lateinit var alarmTextReference: DatabaseReference
    private lateinit var alarmList: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feeding_time, container, false)

        alarmList = view.findViewById(R.id.alarmList)
        layoutManager = LinearLayoutManager(activity)
        alarmList.layoutManager = layoutManager

        val alarms: MutableList<String> = mutableListOf()

        alarmTextReference = FirebaseDatabase.getInstance().reference.child("alarm")

        alarmTextReference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val alarm = p0.getValue(Alarm::class.java)
                val hour = alarm!!.alarmHour
                val minute = alarm!!.alarmMinute
                var hourString = ""
                var minuteString = ""

                if(hour<10) {
                    hourString = "0$hour"
                } else {
                    hourString = "$hour"
                }
                if(minute<10) {
                    minuteString = "0$minute"
                } else {
                    minuteString = "$minute"
                }

                val alarmData = "$hourString : $minuteString"

                alarms.add(alarmData)
                alarms.sort()

                alarmList.adapter = AlarmAdapter(alarms)
                alarmList.layoutManager = LinearLayoutManager(activity)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

        val alarmBtn: Button = view.findViewById(R.id.alarmButton)
        alarmBtn.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
        }

        return view
    }

}