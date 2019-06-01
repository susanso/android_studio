package com.hoseo.autofeeder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.alarm_row.view.*

class AlarmAdapter(private val alarms: MutableList<String>) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = AlarmViewHolder(parent)

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holer: AlarmViewHolder, position: Int) {
        alarms[position].let { item ->
            with(holer) {
                textAlarm.text = "$item"
            }
        }
    }

    inner class AlarmViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.alarm_row, parent, false)) {
        val textAlarm = itemView.textAlarm
    }
}