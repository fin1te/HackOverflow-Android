package com.fin1te.hackoverflow.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentProfileBinding
import java.util.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawableArray = arrayOf(
            R.drawable.z_ph_1, R.drawable.z_ph_2, R.drawable.z_ph_3,
            R.drawable.z_ph_4, R.drawable.z_ph_5, R.drawable.z_ph_6,
            R.drawable.z_ph_7, R.drawable.z_ph_8, R.drawable.z_ph_9, R.drawable.z_ph_10
        )

        val colors = resources.getStringArray(R.array.colorArray)
        val livingBeings = resources.getStringArray(R.array.livingBeingArray)
        val randomColor = colors[Random().nextInt(colors.size)]
        val randomLivingBeing = livingBeings[Random().nextInt(livingBeings.size)]
        val randomName = "$randomColor $randomLivingBeing"

        binding.apply {
            user1name.text = "$randomColor $randomLivingBeing"
            user2name.text = livingBeings[Random().nextInt(livingBeings.size)]
            user3name.text = livingBeings[Random().nextInt(livingBeings.size)]
            user4name.text = livingBeings[Random().nextInt(livingBeings.size)]
        }

        binding.profileImage.setImageResource(drawableArray[Random().nextInt(10)])
        binding.picTeammate1.setImageResource(drawableArray[Random().nextInt(10)])
        binding.picTeammate2.setImageResource(drawableArray[Random().nextInt(10)])
        binding.picTeammate3.setImageResource(drawableArray[Random().nextInt(10)])
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}