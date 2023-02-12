package com.fin1te.hackoverflow.ui

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentFeedBinding
import java.util.regex.Pattern


class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFeedBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textGradient(binding.feedTitle, "#8AFF80", "#81B3FF")
        highlightHashtags(binding.noFeedText)
    }

    fun highlightHashtags(textView: TextView) {
        val text = textView.text.toString()
        val spannable = SpannableString(text)
        val matcher = Pattern.compile("#[A-Za-z]+").matcher(text)

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            spannable.setSpan(ForegroundColorSpan(Color.parseColor("#BEAAEC")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textView.text = spannable
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