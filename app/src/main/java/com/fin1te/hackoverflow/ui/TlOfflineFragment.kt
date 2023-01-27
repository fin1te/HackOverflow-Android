package com.fin1te.hackoverflow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.adapter.EventAdapter
import com.fin1te.hackoverflow.databinding.FragmentTlOfflineBinding
import com.fin1te.hackoverflow.model.Event
import com.fin1te.hackoverflow.model.OfflineEventRepo
import xyz.sangcomz.stickytimelineview.callback.SectionCallback
import xyz.sangcomz.stickytimelineview.model.SectionInfo

class TlOfflineFragment : Fragment() {

    private var _binding: FragmentTlOfflineBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTlOfflineBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVerticalRecyclerView()
    }


    private fun initVerticalRecyclerView() {
        val eventList = getEventList()
        binding.offlineRecView.adapter = EventAdapter(
            layoutInflater,
            eventList,
            R.layout.recycler_vertical_row
        )

        //Currently only LinearLayoutManager is supported.
        binding.offlineRecView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        binding.offlineRecView.apply {
            addItemDecoration(getSectionCallback(eventList))
        }

    }

    //Get data method
    private fun getEventList(): List<Event> = OfflineEventRepo().eventList


    //Get SectionCallback method
    private fun getSectionCallback(eventList: List<Event>): SectionCallback {
        return object : SectionCallback {
            //In your data, implement a method to determine if this is a section.
            override fun isSection(position: Int): Boolean =
                eventList[position].title != eventList[position - 1].title

            //Implement a method that returns a SectionHeader.
            override fun getSectionHeader(position: Int): SectionInfo {
                val event = eventList[position]
                return SectionInfo(event.title, event.subtitle)
            }

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}