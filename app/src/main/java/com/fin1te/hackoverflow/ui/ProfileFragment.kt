package com.fin1te.hackoverflow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentProfileBinding
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {

    private  var binding : FragmentProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(this@ProfileFragment)
            .load("https://xsgames.co/randomusers/assets/avatars/male/77.jpg")
            .centerCrop()
            .into(binding!!.profileImage)
    }
}