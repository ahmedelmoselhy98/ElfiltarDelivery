package com.elmoselhy.elfiltardelivery.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elmoselhy.elfiltardelivery.business.authentication.activities.SignInActivity
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.commons.models.ServerErrorModel
import com.elmoselhy.elfiltardelivery.commons.models.UnAuthorizedErrorModel
import com.elmoselhy.elfiltardelivery.commons.rx.RxBus
import com.elmoselhy.elfiltardelivery.data.local.session.SessionHelper
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable
import www.sanju.motiontoast.MotionToast
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    lateinit var myApp: BaseApp

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