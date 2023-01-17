package com.elmoselhy.elfiltardelivery.business.general.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elmoselhy.elfiltardelivery.base.BaseSheetDialog
import com.elmoselhy.elfiltardelivery.databinding.DialogLayoutLogoutBinding

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


