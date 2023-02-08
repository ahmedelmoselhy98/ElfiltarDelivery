package com.elfiltar.elfiltartechnician.business.general.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.databinding.DialogLayoutLogoutBinding

class LogoutDialog (
    context: Context,
    var onConfirm:(Boolean)->Unit
    ) : BaseSheetDialog(context) {
        var binding: DialogLayoutLogoutBinding =
            DialogLayoutLogoutBinding.inflate(LayoutInflater.from(context))

        init {
            setContentView(binding.root)
            setUpSheetUi(binding.root.parent)
            setUpActions()
        }

    private fun setUpActions() {
        binding.logoutBtn.setOnClickListener {
            onConfirm(true)
            dismiss()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
    }
}


