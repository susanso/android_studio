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


class FeedingTimeFragment : Fragment() {

    /*
        DB의 alarm 주소를 저장할 변수
     */

    private lateinit var alarmReference: DatabaseReference
    private lateinit var alarmList: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
            view들을 변수에 저장
            alarm의 값을 넣을 MutableList alarmList를 선언
         */
        val view = inflater.inflate(R.layout.fragment_feeding_time, container, false)

        alarmList = view.findViewById(R.id.alarmList)
        layoutManager = LinearLayoutManager(activity)
        alarmList.layoutManager = layoutManager

        val alarms: MutableList<String> = mutableListOf()

        alarmReference = FirebaseDatabase.getInstance().reference.child("alarm")

        /*
            DB에서 alarm 값을 불러옴
         */

        alarmReference.addChildEventListener(object : ChildEventListener {
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

                /*
                    서식을 위해 한 자리 숫자도 0을 앞에 넣어 두 자리 숫자로 만듦
                 */

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

                /*
                    불러온 값을 MutableList에 저장하며 시간 순으로 정렬
                 */

                alarms.add(alarmData)
                alarms.sort()

                alarmList.adapter = AlarmAdapter(alarms)
                alarmList.layoutManager = LinearLayoutManager(activity)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

        /*
            화면 전환을 위해 버튼에 onClickListener 설정
         */

        val alarmBtn: Button = view.findViewById(R.id.alarmButton)
        alarmBtn.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
        }

        return view
    }

}