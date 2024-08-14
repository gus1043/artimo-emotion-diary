package com.example.artimo_emotion_diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(
    private val dataSet: ArrayList<Date>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(date: Date)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTv: TextView = view.findViewById(R.id.date_cell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateItem = dataSet[position]
        holder.dateTv.text = dateItem.date

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(dateItem)
        }
    }

    override fun getItemCount() = dataSet.size
}
