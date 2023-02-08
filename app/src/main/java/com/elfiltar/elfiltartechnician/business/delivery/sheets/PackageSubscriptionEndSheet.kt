package com.elfiltar.elfiltartechnician.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutPackageSubscriptionsEndBinding

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