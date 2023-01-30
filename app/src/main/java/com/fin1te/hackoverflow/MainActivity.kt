package com.fin1te.hackoverflow

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fin1te.hackoverflow.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //DataBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        checkForUpdate()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun checkForUpdate() {
        Log.d("checkUpdate", "checkForUpdate: Called")
        val database = Firebase.database.reference
        database.child("versionCode").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("checkUpdate", "onDataChange: Called")
                val latestVersion = dataSnapshot.getValue(String::class.java)
                val manager = this@MainActivity.packageManager
                val info = manager.getPackageInfo(this@MainActivity.packageName, 0)
                val currentVersion = info.versionName.toString()
                Log.d("checkUpdate", "$latestVersion ; $currentVersion")

                if (latestVersion != currentVersion) {
                    val dialogView = layoutInflater.inflate(R.layout.dialog_update, null)
                    val dialog = AlertDialog.Builder(this@MainActivity)
                        .setCancelable(false)
                        .setView(dialogView)
                        .create()

                    dialogView.findViewById<CardView>(R.id.btnUpdate).setOnClickListener {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://phcet.tech/app")
                            )
                        )
                    }

                    dialog.show()
                }


                // Show dialog to prompt user to update the app
//                if (latestVersion != currentVersion) {
//                    val dialog = AlertDialog.Builder(this@MainActivity)
//                        .setCancelable(false)
//                        .setTitle("Update Available")
//                        .setMessage("Please update the app to the latest version to continue using the app")
//                        .setPositiveButton("OK") { _, _ ->
//                            startActivity(
//                                Intent(
//                                    Intent.ACTION_VIEW,
//                                    Uri.parse("https://phcet.tech/app")
//                                )
//                            )
//                        }.create()
//                    dialog.show()
//                            .setPositiveButton("OK") { _, _ ->
//                                val appPackageName = packageName // get the package name
//                                try {
//                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
//                                } catch (e: ActivityNotFoundException) {
//                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
//                                }
//                            }

//                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}