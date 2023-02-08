package com.elfiltar.elfiltartechnician.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutCancelOrderBinding

class CancelOrderSheet(
    private val mContext: Context,
    val onConfirm: (String) -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutCancelOrderBinding =
        SheetLayoutCancelOrderBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
    }

    private fun setUpActions() {
        binding.btnConfirm.setOnClickListener {
            if (!binding.etNotes.isValid) {
                binding.etNotes.error = mContext.getString(R.string.error_message_required)
                return@setOnClickListener
            }
            onConfirm(binding.etNotes.text.toString())
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}