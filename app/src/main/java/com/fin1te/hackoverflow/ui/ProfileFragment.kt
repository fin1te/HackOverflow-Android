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
import android.widget.Toast
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
        if (email != null && name != null) {
            binding.googleSignInText.text = "Signed In"
        } else {
            binding.googleSignInText.text = "Sign in with Google"
        }

        initializeDefaultProfiles()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.logoutBtn.setOnClickListener {
            googleSignInClient.signOut()
            Toast.makeText(requireContext(), "Signed Out", Toast.LENGTH_SHORT).show()
            binding.googleSignInText.text = "Sign in with Google"
            val sharedPref = requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
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
                    // Do something with the user's email and name
                    val sharedPref = requireActivity().getSharedPreferences("signInData", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("email", email)
                    editor.putString("name", name)
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
    private fun initializeDefaultProfiles() {
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