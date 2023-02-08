package com.elfiltar.elfiltartechnician.business.authentication.sheet

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutOtpBinding
import www.sanju.motiontoast.MotionToast

open class OtpSheet(
    private val mContext: Context,
    private val phone: String,
    val onResendCode: () -> Unit,
    val onConfirm: (String) -> Unit,
) : BaseSheetDialog(mContext) {

    var binding: SheetLayoutOtpBinding =
        SheetLayoutOtpBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
    }

    private fun setUpActions() {
        showCountDownResendButton()
        binding.tvPhone.text = phone
        binding.tvResendCode.setOnClickListener {
            onResendCode()
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            if (binding.otpView.otp.isNullOrEmpty()) {
                MyUtils.shoMsg(
                    mContext as BaseActivity,
                    context.getString(R.string.otp_is_required),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            }
            onConfirm(binding.otpView.otp!!)
            dismiss()
        }
    }

    private fun showCountDownResendButton() {
        binding.tvResendCode.isEnabled = false
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "00:" + (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.tvResendCode.isEnabled = true
            }
        }.start()
    }
}