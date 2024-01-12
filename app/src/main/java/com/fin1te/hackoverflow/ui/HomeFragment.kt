package com.fin1te.hackoverflow.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.fin1te.hackoverflow.adapter.TimelinePagerAdapters
import com.fin1te.hackoverflow.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pTabs: TabLayout
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: TimelinePagerAdapters
    private var countDownTimer: CountDownTimer? = null

    private var timerTitle = "Hackathon Starts In"
    private var timerDate = 14
    private var timerHour = 12
    private var timerMinute = 0
    private var timerSecond = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTimerDetailsFromFirebase()

        //startCountDown() // Starts the countdown timer

//        binding.countDownTimerTitle.text = timerTitle
//
//        textGradient(binding.countDownTimerTitle, "#80FFEA", "#9580FF")
        textGradient(binding.timelineTitle, "#FFFF80", "#FF80BF")



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

    private fun getTimerDetailsFromFirebase() {
        // TODO: Add time caching for the situation when no internet or DB error is there,
        //  and show last fetched data, and add swipeRefreshLayout

        val database = Firebase.database.reference

        database.child("homeCountdownTimer").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                timerTitle = snapshot.child("title").value.toString()
                timerDate = Integer.parseInt(snapshot.child("date").value.toString())
                timerHour = Integer.parseInt(snapshot.child("hour").value.toString())
                timerMinute = Integer.parseInt(snapshot.child("minute").value.toString())

                startCountDown()
                binding.countDownTimerTitle.text = timerTitle
                textGradient(binding.countDownTimerTitle, "#80FFEA", "#9580FF")
            }

            override fun onCancelled(error: DatabaseError) {
                startCountDown()
                binding.countDownTimerTitle.text = timerTitle
                textGradient(binding.countDownTimerTitle, "#80FFEA", "#9580FF")
            }

        })
    }


    private fun startCountDown() {
        val targetDate = Calendar.getInstance().apply {
            set(2024, Calendar.MARCH, timerDate, timerHour, timerMinute, timerSecond)
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

    private fun textGradient(textView: TextView, color1: String, color2: String) {
        val paint = textView.paint
        val height = paint.fontSpacing
        val textShader = LinearGradient(
            0f, 0f, 0f, height, intArrayOf(
                Color.parseColor(color1), Color.parseColor(color2)
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}