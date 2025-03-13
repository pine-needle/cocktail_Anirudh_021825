package com.action.cocktail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.action.cocktail.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val TAG: String? = "MainActivityLogs"
    private lateinit var binding: ActivityMainBinding

    // Register the permissions callback for handling the permission request result
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission is granted. You can proceed with your notification logic.
            Log.d(TAG, "Notification permission granted")
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
        } else {
            // Permission is denied. You can inform the user or handle accordingly.
            Log.d(TAG, "Notification permission denied")
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (LeakCode.contextRef == null) LeakCode.contextRef = this
        else Toast.makeText(LeakCode.contextRef, "Already got it ", Toast.LENGTH_SHORT).show()

        // Get registration token
        getFirebaseToken()

        // Request notification permission if running on Android 13 or higher
        requestNotificationPermission()
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            } else { // Get new FCM registration token
                val token = task.result

                // Log and toast
                Log.d(TAG, "Token Received: $token")
                Toast.makeText(baseContext, "Got the token!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted
                    Log.d(TAG, "Notification permission already granted")
                    Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Request the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // For devices below Android 13, notification permissions are not required
            Log.d(TAG, "Notification permission not required for this Android version")
            Toast.makeText(this, "Notification permission not required for this Android version", Toast.LENGTH_SHORT).show()
        }
    }
}

object LeakCode{
    var contextRef: Context? = null
}