package com.fin1te.hackoverflow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fin1te.hackoverflow.databinding.RecyclerVerticalRowBinding
import com.fin1te.hackoverflow.model.Event

class EventAdapter(
    private val layoutInflater: LayoutInflater,
    private val eventList: List<Event>,
    @param:LayoutRes private val rowLayout: Int
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = RecyclerVerticalRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = eventList.size

    class ViewHolder(
        private val binding: RecyclerVerticalRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.fullNameTv.text = event.points
        }
    }
}