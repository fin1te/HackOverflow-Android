package com.fin1te.hackoverflow.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.fin1te.hackoverflow.MainActivity
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.adapter.TimelinePagerAdapters
import com.fin1te.hackoverflow.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pTabs: TabLayout
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: TimelinePagerAdapters
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCountDown() // Starts the countdown timer

        binding.apply {

            this@HomeFragment.pViewPager = myPagerView
            this@HomeFragment.pTabs = tabs

            pagerAdapters = TimelinePagerAdapters(childFragmentManager)

            pagerAdapters.addFragment(TlOfflineFragment(), "Offline")
            pagerAdapters.addFragment(TlOnlineFragment(), "Online")

            pViewPager.adapter = pagerAdapters

            pTabs.setupWithViewPager(pViewPager)
            // Tab Icons
//            pTabs.getTabAt(0)!!.setIcon(R.drawable.offlineIcon)
//            pTabs.getTabAt(1)!!.setIcon(R.drawable.onlineIcon)
        }

    }


    private fun startCountDown() {
        val targetDate = Calendar.getInstance().apply {
            set(2023, Calendar.MARCH, 16, 0, 0, 0)
        }.time

        countDownTimer?.cancel()
        countDownTimer =
            object : CountDownTimer(targetDate.time - System.currentTimeMillis(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                    val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                    val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                    //Do whatever you want with the time left
                    //for example updating a text view
                    binding.apply {
                        daysCircular.progress = days.toFloat()
                        hoursCircular.progress = hours.toFloat()
                        minutesCircular.progress = minutes.toFloat()
                        secondsCircular.progress = seconds.toFloat()
                        daysCircular.setText("$days")
                        hoursCircular.setText("$hours")
                        minutesCircular.setText("$minutes")
                        secondsCircular.setText("$seconds")
                        daysCircular.setClockwise(true)
                        hoursCircular.setClockwise(true)
                        minutesCircular.setClockwise(true)
                        secondsCircular.setClockwise(true)
                    }
                }

                override fun onFinish() {
                    //Do whatever you want when the countdown finishes
                }
            }.start()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}