package com.elfiltar.elfiltartechnician.business.general.activities

import android.content.Intent
import android.util.Log
import android.view.View
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.authentication.activities.SignInActivity
import com.elfiltar.elfiltartechnician.business.delivery.activities.MainActivity
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivitySplashBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity : BaseActivity() {

    //declare properties
    lateinit var binding: ActivitySplashBinding

    override fun setUpLayoutView(): View {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
    }

    override fun onStart() {
        initFirebaseToken()
        super.onStart()
    }

    private fun initFirebaseToken() {
        FirebaseApp.initializeApp(this)
        if (sessionHelper.getPushNotificationToken().isNullOrEmpty()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "addOnCompleteListener",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result
                // Log and toast
                Log.e("FCM", "Current token=$token")
                sessionHelper.savePushNotificationToken(token)
                MyUtils.executeDelay(2000) {
                    if (sessionHelper.getUserSession() != null) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                }
            })
        } else {
            MyUtils.executeDelay(2000) {
                if (sessionHelper.getUserSession() != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }
    }
}