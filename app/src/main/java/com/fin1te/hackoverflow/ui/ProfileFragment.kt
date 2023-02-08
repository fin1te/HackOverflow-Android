package com.fin1te.hackoverflow.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentProfileBinding
import com.fin1te.hackoverflow.model.Member
import com.fin1te.hackoverflow.model.Team
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import java.util.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val RC_SIGN_IN = 9001
    private var isSignedIn = false
    private lateinit var teamObj: Team
    private lateinit var googleSignInClient: GoogleSignInClient


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


        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Check if user is already signed in
        val teamPref = requireContext().getSharedPreferences("team_pref", Context.MODE_PRIVATE)
        val teamJson = teamPref.getString("team", "")
        Log.d("isTeamsEmpty", teamJson?.isEmpty().toString()) // true when no one signed in.
        val gson = Gson()

        initializeNames(binding.user1name, binding.user2name, binding.user3name, binding.user4name)
        initializePics(
            binding.profileImage,
            binding.picTeammate1,
            binding.picTeammate2,
            binding.picTeammate3
        )
        binding.user3card.visibility = View.GONE
        binding.user4card.visibility = View.GONE

        textGradient(binding.user1name, "#80FFEA", "#9580FF")
        binding.googleSignInText.text = getString(R.string.sign_in_with_google)

        if (!teamJson.isNullOrEmpty()) {
            // We have team object saved in shared pref, so we update name and pics using team object
            isSignedIn = true
            val team = gson.fromJson(teamJson, Team::class.java)
            binding.googleSignInText.text = getString(R.string.signed_in)
            updateUIwithTeam(team)
        } else {
            binding.user3card.visibility = View.VISIBLE
            binding.user4card.visibility = View.VISIBLE
        }

        // Sign in with Google
        binding.googleSignIn.setOnClickListener {

            if (isSignedIn) {
                Toast.makeText(requireContext(), "Already Signed In", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isInternetConnected()) {
                // show error snackbar
                val snackBar = Snackbar.make(
                    requireView(),
                    "No Internet Connection",
                    Snackbar.LENGTH_LONG
                )
                snackBar.show()
                return@setOnClickListener
            }

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // Logout
        binding.logoutBtn.setOnClickListener {

            if (!isSignedIn) {
                Toast.makeText(requireContext(), "Already Signed Out", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show logout dialog box if user is signed in
            val logoutDialogView = layoutInflater.inflate(R.layout.dialog_logout, null)
            val logoutDialog = AlertDialog.Builder(requireContext())
                .setCancelable(true)
                .setView(logoutDialogView)
                .create()

            logoutDialogView.findViewById<CardView>(R.id.btnLogoutNo).setOnClickListener {
                logoutDialog.dismiss()
            }

            logoutDialogView.findViewById<CardView>(R.id.btnLogoutYes).setOnClickListener {

                // Sign out from Google and clear shared preferences
                googleSignInClient.signOut()
                isSignedIn = false
                binding.googleSignInText.text = getString(R.string.sign_in_with_google)
                Toast.makeText(requireContext(), "Signed Out", Toast.LENGTH_SHORT).show()

                if (binding.user3card.visibility == View.GONE) {
                    binding.user3card.visibility = View.VISIBLE
                }
                if (binding.user4card.visibility == View.GONE) {
                    binding.user4card.visibility = View.VISIBLE
                }

                // Team data is cleared from shared preferences
                val teamPref = context?.getSharedPreferences("team_pref", Context.MODE_PRIVATE)
                val teamEditor = teamPref?.edit()
                teamEditor?.clear()
                teamEditor?.apply()

                initializeNames(
                    binding.user1name,
                    binding.user2name,
                    binding.user3name,
                    binding.user4name
                )
                initializePics(
                    binding.profileImage,
                    binding.picTeammate1,
                    binding.picTeammate2,
                    binding.picTeammate3
                )



                logoutDialog.dismiss()
            }
            logoutDialog.show()
        }

        // User 1 Profile On Click
        binding.profileImage.setOnClickListener {
            if (isSignedIn) {
                val teamPref =
                    requireContext().getSharedPreferences("team_pref", Context.MODE_PRIVATE)
                val teamJson = teamPref.getString("team", "")
                val team = gson.fromJson(teamJson, Team::class.java)
                val bundle = Bundle()
                bundle.putString("name", team.members[0].name)
                bundle.putString("email", team.members[0].email)
                bundle.putString("avatarURL", team.members[0].avUrl)
                bundle.putString("category", team.category)
                bundle.putString("teamName", team.teamName)

                findNavController().navigate(R.id.action_profileFragment_to_ticketFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please Sign In", Toast.LENGTH_SHORT).show()
            }
        }

        // User 2 Profile On Click
        binding.user2card.setOnClickListener {
            if (isSignedIn) {
                val teamPref =
                    requireContext().getSharedPreferences("team_pref", Context.MODE_PRIVATE)
                val teamJson = teamPref.getString("team", "")
                val team = gson.fromJson(teamJson, Team::class.java)
                val bundle = Bundle()
                bundle.putString("name", team.members[1].name)
                bundle.putString("email", team.members[1].email)
                bundle.putString("avatarURL", team.members[1].avUrl)
                bundle.putString("category", team.category)
                bundle.putString("teamName", team.teamName)

                findNavController().navigate(R.id.action_profileFragment_to_ticketFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please Sign In", Toast.LENGTH_SHORT).show()
            }
        }

        // User 3 Profile On Click
        binding.user3card.setOnClickListener {
            if (isSignedIn) {
                val teamPref =
                    requireContext().getSharedPreferences("team_pref", Context.MODE_PRIVATE)
                val teamJson = teamPref.getString("team", "")
                val team = gson.fromJson(teamJson, Team::class.java)
                val bundle = Bundle()
                bundle.putString("name", team.members[2].name)
                bundle.putString("email", team.members[2].email)
                bundle.putString("avatarURL", team.members[2].avUrl)
                bundle.putString("category", team.category)
                bundle.putString("teamName", team.teamName)

                findNavController().navigate(R.id.action_profileFragment_to_ticketFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please Sign In", Toast.LENGTH_SHORT).show()
            }
        }

        // User 4 Profile On Click
        binding.user4card.setOnClickListener {
            if (isSignedIn) {
                val teamPref =
                    requireContext().getSharedPreferences("team_pref", Context.MODE_PRIVATE)
                val teamJson = teamPref.getString("team", "")
                val team = gson.fromJson(teamJson, Team::class.java)
                val bundle = Bundle()
                bundle.putString("name", team.members[3].name)
                bundle.putString("email", team.members[3].email)
                bundle.putString("avatarURL", team.members[3].avUrl)
                bundle.putString("category", team.category)
                bundle.putString("teamName", team.teamName)

                findNavController().navigate(R.id.action_profileFragment_to_ticketFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please Sign In", Toast.LENGTH_SHORT).show()
            }
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
                    val avatarURL = if (account.photoUrl != null) account.photoUrl.toString()
                        .replace("s96", "s360") else null

                    val database = FirebaseDatabase.getInstance()
                    val ref = database.getReference("users")

                    ref.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.children.forEach { categorySnapshot ->
                                categorySnapshot.children.forEach { teamSnapshot ->
                                    teamSnapshot.children.forEach { memberSnapshot ->
                                        if (memberSnapshot.child("email").value == email) {

                                            // USER FOUND IN DATABASE

                                            isSignedIn = true
                                            binding.googleSignInText.text =
                                                getString(R.string.signed_in)

                                            if (avatarURL != null) {
                                                memberSnapshot.ref.child("avUrl")
                                                    .setValue(avatarURL)
                                                Log.d(
                                                    "checkFirebase",
                                                    "avatarURL updated to Firebase"
                                                )
                                            }

                                            // Fetch team data in variables
                                            val teamName = teamSnapshot.key
                                            val category = categorySnapshot.key
                                            val name = memberSnapshot.child("name").value as String
                                            val avUrl = avatarURL
                                                ?: memberSnapshot.child("avUrl").value as String
                                            val id = memberSnapshot.child("id").value as String
                                            val phone =
                                                memberSnapshot.child("phone").value as String

                                            val team = Team(
                                                teamName!!,
                                                category!!,
                                                mutableListOf(
                                                    Member(name, email!!, avUrl, id, phone)
                                                )
                                            )

                                            // Temporarily directly saving AvURL from Google Auth to SharedPref
//                                            val avUrl = memberSnapshot.child("avUrl").value as String
                                            // TODO LATER : fetch it from Firebase after the avUrl gets updated there


                                            // Fetch other team members
                                            teamSnapshot.children.forEach { otherMemberSnapshot ->
                                                if (otherMemberSnapshot.key != memberSnapshot.key) {
                                                    val otherName =
                                                        otherMemberSnapshot.child("name").value as String
                                                    val otherEmail =
                                                        otherMemberSnapshot.child("email").value as String
                                                    val otherAvUrl =
                                                        otherMemberSnapshot.child("avUrl").value as String
                                                    val otherId =
                                                        otherMemberSnapshot.child("id").value as String
                                                    val otherPhone =
                                                        otherMemberSnapshot.child("phone").value as String

                                                    team.addMember(
                                                        Member(
                                                            otherName,
                                                            otherEmail,
                                                            otherAvUrl,
                                                            otherId,
                                                            otherPhone
                                                        )
                                                    )
                                                }
                                            }

                                            // Save team to shared preferences
                                            val sharedPref = context?.getSharedPreferences(
                                                "team_pref",
                                                Context.MODE_PRIVATE
                                            )
                                            val editor = sharedPref?.edit()

                                            val gson = Gson()
                                            val teamJson = gson.toJson(team)
                                            teamObj = gson.fromJson(teamJson, Team::class.java)

                                            editor?.putString("team", teamJson)
                                            editor?.apply()

                                        }
                                    }
                                }
                            }

                            if (!isSignedIn) {
                                Toast.makeText(
                                    requireContext(),
                                    "Email not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                                googleSignInClient.signOut()
                            } else {
                                updateUIwithTeam(teamObj)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
            } catch (e: ApiException) {
                // Handle the error
            }
        }
    }


    // check how many members are in the team and update name & avatar
    private fun updateUIwithTeam(team: Team) {

        binding.user3card.visibility = View.GONE
        binding.user4card.visibility = View.GONE

        val teamMembers = team.members
        for (i in 0 until teamMembers.size) {
            when (i) {
                0 -> {
                    binding.user1name.text = teamMembers[i].name
                    if (teamMembers[i].avUrl.isNotEmpty()) {
                        Glide.with(requireContext()).load(teamMembers[i].avUrl)
                            .into(binding.profileImage)
                    }
                }

                1 -> {
                    binding.user2name.text = teamMembers[i].name.split(" ").first()
                    if (teamMembers[i].avUrl.isNotEmpty()) {
                        Glide.with(requireContext()).load(teamMembers[i].avUrl)
                            .into(binding.picTeammate1)
                    }
                }

                2 -> {
                    binding.user3name.text = teamMembers[i].name.split(" ").first()
                    if (teamMembers[i].avUrl.isNotEmpty()) {
                        Glide.with(requireContext()).load(teamMembers[i].avUrl)
                            .into(binding.picTeammate2)
                    }
                    binding.user3card.visibility = View.VISIBLE
                }

                3 -> {
                    binding.user4name.text = teamMembers[i].name.split(" ").first()
                    if (teamMembers[i].avUrl.isNotEmpty()) {
                        Glide.with(requireContext()).load(teamMembers[i].avUrl)
                            .into(binding.picTeammate3)
                    }
                    binding.user4card.visibility = View.VISIBLE
                }
            }
        }
    }

    // check if internet is available or not and return true or false
    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }


    // initialize pics with random pics
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

    // initialize names with random names
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

    private fun textGradient(textView: TextView, color1: String, color2: String) {
        val paint = textView.paint
        val height = paint.fontSpacing
        val textShader = LinearGradient(
            0f, 0f, 0f, height,
            intArrayOf(
                Color.parseColor(color1),
                Color.parseColor(color2)
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}