package com.fin1te.hackoverflow.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import java.util.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if user is signed in
        val sharedPref = requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)
        val name = sharedPref.getString("name", null)
        val avatarURL = sharedPref.getString("avatarURL", null)
        if (email != null && name != null) {
            binding.googleSignInText.text = "Signed In"
            binding.user1name.text = name
            initializeNames(null, binding.user2name, binding.user3name, binding.user4name)
        } else {
            initializeNames(binding.user1name, binding.user2name, binding.user3name, binding.user4name)
            binding.googleSignInText.text = "Sign in with Google"
        }
        if (avatarURL?.isNotEmpty() == true) {
            Glide.with(requireContext()).load(avatarURL).into(binding.profileImage)
            initializePics(null, binding.picTeammate1, binding.picTeammate2, binding.picTeammate3)
        } else {
            initializePics(binding.profileImage, binding.picTeammate1, binding.picTeammate2, binding.picTeammate3)
        }



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleSignIn.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
            val email = sharedPref.getString("email", null)
            val name = sharedPref.getString("name", null)

            if (email != null && name != null) {
                Toast.makeText(requireContext(), "Already Signed In", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.logoutBtn.setOnClickListener {

            val sharedPref = requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
            val email = sharedPref.getString("email", null)
            val name = sharedPref.getString("name", null)
            if (email == null && name == null) {
                Toast.makeText(requireContext(), "Already Signed Out", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            googleSignInClient.signOut()
            Toast.makeText(requireContext(), "Signed Out", Toast.LENGTH_SHORT).show()
            binding.googleSignInText.text = "Sign in with Google"
//            val sharedPref =
//                requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            initializeNames(binding.user1name, null, null, null)
            initializePics(binding.profileImage, null, null, null)
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val email = account.email
                    val name = account.displayName
                    val avatarURL =
                        if (account.photoUrl != null) account.photoUrl.toString().replace("s96-c", "s350-c") else ""
                    if (avatarURL.isNotEmpty()) {
                        Glide.with(requireContext()).load(avatarURL).into(binding.profileImage)
                    }
                    binding.user1name.text = name
                    // Do something with the user's email and name
                    val sharedPref =
                        requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("email", email)
                    editor.putString("name", name)
                    editor.putString("avatarURL", avatarURL)
                    editor.apply()
                    binding.googleSignInText.text = "Signed In"
                    Toast.makeText(requireContext(), "Signed In", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                // Handle the error
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initializePics(
        p1: ImageView? = null,
        p2: ImageView? = null,
        p3: ImageView? = null,
        p4: ImageView? = null
    ) {

        val drawableArray = arrayOf(
            R.drawable.z_ph_1, R.drawable.z_ph_2, R.drawable.z_ph_3,
            R.drawable.z_ph_4, R.drawable.z_ph_5, R.drawable.z_ph_6,
            R.drawable.z_ph_7, R.drawable.z_ph_8, R.drawable.z_ph_9, R.drawable.z_ph_10
        )

        binding.apply {
            p1?.setImageResource(drawableArray[Random().nextInt(10)])
            p2?.setImageResource(drawableArray[Random().nextInt(10)])
            p3?.setImageResource(drawableArray[Random().nextInt(10)])
            p4?.setImageResource(drawableArray[Random().nextInt(10)])
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initializeNames(
        p1: TextView? = null,
        p2: TextView? = null,
        p3: TextView? = null,
        p4: TextView? = null
    ) {
        val colors = resources.getStringArray(R.array.colorArray)
        val livingBeings = resources.getStringArray(R.array.livingBeingArray)
        val randomColor = colors[Random().nextInt(colors.size)]
        val randomLivingBeing = livingBeings[Random().nextInt(livingBeings.size)]

        binding.apply {
            p1?.text = "$randomColor $randomLivingBeing"
            p2?.text = livingBeings[Random().nextInt(livingBeings.size)]
            p3?.text = livingBeings[Random().nextInt(livingBeings.size)]
            p4?.text = livingBeings[Random().nextInt(livingBeings.size)]
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}