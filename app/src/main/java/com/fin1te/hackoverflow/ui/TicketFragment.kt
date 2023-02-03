package com.fin1te.hackoverflow.ui

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fin1te.hackoverflow.MainActivity
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentTicketBinding

class TicketFragment : Fragment() {

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentTicketBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Purple Cyan
        // textGradient(binding.hackoverflowTitle, "#80FFEA", "#9580FF")

        // Pink Purple
        textGradient(binding.hackoverflowTitle, "#9580FF", "#FF80BF")
        textGradient(binding.userName, "#FFFF80", "#FF80BF")
        textGradient(binding.category, "#8AFF80", "#81B3FF")
        // Yellow Orange
        // textGradient(binding.hackoverflowTitle, "#FFFF80", "#FF80BF")



    }


    private fun textGradient(textView: TextView, color1: String, color2: String) {
        val paint = textView.paint
        val height = paint.fontSpacing
        val textShader = LinearGradient(0f, 0f, 0f, height,
            intArrayOf(
                Color.parseColor(color1),
                Color.parseColor(color2)
            ), null, Shader.TileMode.CLAMP)
        textView.paint.shader = textShader
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}