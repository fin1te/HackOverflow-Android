package com.fin1te.hackoverflow.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentHomeBinding
import com.fin1te.hackoverflow.databinding.FragmentProfileBinding
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCountDown() // Starts the countdown timer


    }

    private fun startCountDown() {
        val targetDate = Calendar.getInstance().apply {
            set(2023, Calendar.MARCH, 16, 0, 0, 0)
        }.time

        val countDownTimer = object : CountDownTimer(targetDate.time - System.currentTimeMillis(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                //Do whatever you want with the time left
                //for example updating a text view
                binding!!.apply {
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
}