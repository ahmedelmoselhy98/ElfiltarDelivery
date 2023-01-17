package com.elmoselhy.elfiltardelivery.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.base.BaseSheetDialog
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.SheetLayoutAddAlarmBinding
import com.elmoselhy.elfiltardelivery.databinding.SheetLayoutCancelOrderBinding
import www.sanju.motiontoast.MotionToast

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