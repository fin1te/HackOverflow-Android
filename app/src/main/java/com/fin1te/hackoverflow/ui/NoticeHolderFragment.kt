package com.fin1te.hackoverflow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.adapter.NoticePagerAdapters
import com.fin1te.hackoverflow.adapter.TimelinePagerAdapters
import com.fin1te.hackoverflow.databinding.FragmentNoticeHolderBinding
import com.google.android.material.tabs.TabLayout


class NoticeHolderFragment : Fragment() {

    private var _binding: FragmentNoticeHolderBinding? = null
    private val binding get() = _binding!!
    private lateinit var noticeTabs: TabLayout
    private lateinit var noticeViewPager: ViewPager
    private lateinit var noticePagerAdapters: NoticePagerAdapters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentNoticeHolderBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {

            this@NoticeHolderFragment.noticeViewPager = noticePagerView
            this@NoticeHolderFragment.noticeTabs = noticeTabLayout

            noticePagerAdapters = NoticePagerAdapters(childFragmentManager)

            noticePagerAdapters.addFragment(OfflineNoticeFragment(),"Offline")
            noticePagerAdapters.addFragment(OnlineNoticeFragment(),"Online")

            noticeViewPager.adapter = noticePagerAdapters

            noticeTabs.setupWithViewPager(noticeViewPager)
            // Tab Icons
//            pTabs.getTabAt(0)!!.setIcon(R.drawable.offlineIcon)
//            pTabs.getTabAt(1)!!.setIcon(R.drawable.onlineIcon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}