package com.elfiltar.elfiltartechnician.base

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.elfiltar.elfiltartechnician.business.authentication.activities.SignInActivity
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.commons.models.ServerErrorModel
import com.elfiltar.elfiltartechnician.commons.models.UnAuthorizedErrorModel
import com.elfiltar.elfiltartechnician.commons.rx.RxBus
import com.elfiltar.elfiltartechnician.data.local.session.SessionHelper
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable
import www.sanju.motiontoast.MotionToast
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    lateinit var myApp: BaseApp
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Inject
    lateinit var sessionHelper: SessionHelper
    lateinit var eventDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = this.applicationContext as BaseApp
        sessionHelper.configLanguage(this)
        setContentView(setUpLayoutView());
        init()
        handleEvents()
        setUpNotification()
    }

    fun setUpNotification() {
        var notificationManager = NotificationManagerCompat.from(this);
        if (!notificationManager.areNotificationsEnabled()) {
            var intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
            startActivity(intent);
        } else {
            Log.e("permission", "success")
            // App has permission to post notifications
        }
    }

    private fun handleEvents() {
        eventDisposable = RxBus.listen(UnAuthorizedErrorModel::class.java)
            .subscribe() { unAuth ->
                MyUtils.shoMsg(this, "Session Expired!!", MotionToast.TOAST_ERROR)
                sessionHelper.clearUserSession(this)
                finishAffinity()
                startActivity(Intent(this, SignInActivity::class.java))
            }
        eventDisposable = RxBus.listen(ServerErrorModel::class.java)
            .subscribe() { message ->
                MyUtils.shoMsg(this, message.message!!, MotionToast.TOAST_ERROR)
            }
    }

    protected abstract fun setUpLayoutView(): View
    protected abstract fun init()
    override fun onDestroy() {
        if (!eventDisposable.isDisposed) eventDisposable.dispose()
        super.onDestroy()
    }
}