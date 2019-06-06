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



class AlarmAdapter(private val alarms: MutableList<String>) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = AlarmViewHolder(parent)

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holer: AlarmViewHolder, position: Int) {
        alarms[position].let { item ->
            with(holer) {
                textAlarm.text = "$item"

                removeButton.setOnClickListener {
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
                                    key = postSnapshot.key
                                    Log.d("AlarmRemove", "$key")
                                }
                                i++
                            }

                            val alarmItemQuery: Query = alarmReference.orderByKey().equalTo(key)
                            alarmItemQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (postSnapshot in dataSnapshot.children) {
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

    inner class AlarmViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.alarm_row, parent, false)) {
        val textAlarm = itemView.textAlarm
        val removeButton = itemView.removeButton
    }
}