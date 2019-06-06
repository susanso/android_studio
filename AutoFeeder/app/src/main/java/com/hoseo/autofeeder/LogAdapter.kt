package com.hoseo.autofeeder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.log_row.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class LogAdapter(private val logs: MutableList<Long>) : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = LogViewHolder(parent)

    override fun getItemCount(): Int = logs.size

    override fun onBindViewHolder(holer: LogViewHolder, position: Int) {
        logs[position].let { item ->
            with(holer) {
                val cal = Calendar.getInstance()
                cal.setTimeInMillis(item * 1000L)
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val datetime = df.format(cal.time)

                textLog.text = "$datetime 공급"
            }
        }
    }

    inner class LogViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.log_row, parent, false)) {
        val textLog = itemView.textLog
    }
}