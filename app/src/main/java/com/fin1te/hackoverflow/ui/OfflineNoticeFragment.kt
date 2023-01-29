package com.fin1te.hackoverflow.ui

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentOfflineNoticeBinding


class OfflineNoticeFragment : Fragment() {

    private var _binding: FragmentOfflineNoticeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentOfflineNoticeBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val noNoticeArray = requireContext().resources.getStringArray(R.array.noNoticeArray)
        val noNoticeText = noNoticeArray.random()
        typewriterAnimation(binding.noNoticeText, noNoticeText)
    }

    override fun onPause() {
        super.onPause()
        binding.noNoticeText.text = ""
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun typewriterAnimation(textView: TextView, text: String) {
        val textLength = text.length
        val animationInterval = 75L // interval between each letter being added

        textView.text = "" // clear the TextView
        val textAnimator = ValueAnimator.ofInt(0, textLength)
        textAnimator.duration = (textLength * animationInterval).toLong() // duration of animation is determined by text length
        textAnimator.addUpdateListener { valueAnimator ->
            val textIndex = valueAnimator.animatedValue as Int
            textView.text = text.substring(0, textIndex)
        }
        textAnimator.start()
    }
}