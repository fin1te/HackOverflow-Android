package com.fin1te.hackoverflow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.model.Event

class EventAdapter(private val layoutInflater: LayoutInflater,
                   private val eventList: List<Event>,
                   @param:LayoutRes private val rowLayout: Int) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = layoutInflater.inflate(rowLayout,
            parent,
            false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        holder.fullName.text = event.points
    }

    override fun getItemCount(): Int = eventList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullName: TextView = view.findViewById<View>(R.id.full_name_tv) as TextView

    }
}