package com.elmoselhy.elfiltardelivery.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.base.BaseSheetDialog
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.SheetLayoutAddAlarmBinding
import com.elmoselhy.elfiltardelivery.databinding.SheetLayoutPackageSubscriptionsEndBinding
import www.sanju.motiontoast.MotionToast

class PackageSubscriptionEndSheet(
    private val mContext: Context,
    val onConfirm: () -> Unit,
    val onLogout: () -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutPackageSubscriptionsEndBinding =
        SheetLayoutPackageSubscriptionsEndBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
        setCancelable(false)
    }

    private fun setUpActions() {
        binding.btnChoose.setOnClickListener {
            onConfirm()
            dismiss()
        }
        binding.btnLogout.setOnClickListener {
            onLogout()
        }
    }
}