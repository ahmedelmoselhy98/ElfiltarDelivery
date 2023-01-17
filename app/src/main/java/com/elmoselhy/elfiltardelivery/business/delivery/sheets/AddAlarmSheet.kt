package com.elmoselhy.elfiltardelivery.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.base.BaseSheetDialog
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.SheetLayoutAddAlarmBinding
import www.sanju.motiontoast.MotionToast

class AddAlarmSheet(
    private val mContext: Context,
    private val queryMap: HashMap<String, Any>,
    val onConfirm: () -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutAddAlarmBinding =
        SheetLayoutAddAlarmBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
    }

    private fun setUpActions() {
        binding.btnConfirm.setOnClickListener {
            if (!binding.tvDay.isValid) {
                MyUtils.shoMsg(
                    mContext as BaseActivity,
                    context.getString(R.string.must_choose_day),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            }
            if (!binding.etNotes.isValid) {
                binding.etNotes.error = mContext.getString(R.string.error_message_required)
                return@setOnClickListener
            }
            queryMap["notes"] = binding.etNotes.text.toString()
            queryMap["date"] = binding.tvDay.apiDate
            onConfirm()
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}