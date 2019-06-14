package com.hoseo.autofeeder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.alarm_row.view.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


/*
    알람을 표시할 Recycler View에 연결할 Adapter와 View Holder를 만드는 코드
 */


class AlarmAdapter(private val alarms: MutableList<String>) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = AlarmViewHolder(parent)

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holer: AlarmViewHolder, position: Int) {
        alarms[position].let { item ->
            with(holer) {
                textAlarm.text = "$item"

                removeButton.setOnClickListener {
                    /*
                        삭제 버튼을 누르면 해당 번호에 해당하는 항목을 화면에서 삭제
                     */
                    alarms.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, alarms.size)

                    val alarmReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("alarm")
                    val alarmQuery: Query = alarmReference.orderByChild("alarmHour")
                    var key: String? = ""
                    alarmQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var i = 0
                            for (postSnapshot in dataSnapshot.children) {
                                if(i == position) {
                                    /*
                                        DB에서 항목 번호와 일치하는 번호를 가진 alarm의 키 값을 불러옴
                                     */
                                    key = postSnapshot.key
                                    Log.d("AlarmRemove", "$key")
                                }
                                i++
                            }

                            /*
                                불러온 키 값에 해당하는 키 값을 가진 alarm을 찾아 쿼리로 생성
                             */

                            val alarmItemQuery: Query = alarmReference.orderByKey().equalTo(key)
                            alarmItemQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (postSnapshot in dataSnapshot.children) {
                                        /*
                                            쿼리문에 들어온 alarm의 값을 삭제
                                         */
                                        val value = postSnapshot.value
                                        Log.d("AlarmRemove", "$value")
                                        postSnapshot.ref.removeValue()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                }
                            })
                        }

                    })
                }
            }
        }
    }

    /*
        alarm_row.xml의 레이아웃을 바탕으로 레이아웃을 생성할 ViewHolder
     */
    inner class AlarmViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.alarm_row, parent, false)) {
        val textAlarm = itemView.textAlarm
        val removeButton = itemView.removeButton
    }
}