package com.elfiltar.elfiltartechnician.business.general.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutDatePickerBinding
import java.util.Calendar
import java.util.Date

class CustomDatePickerSheet(
    val mContext: Context,
    val onConfirm: (Date) -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutDatePickerBinding =
        SheetLayoutDatePickerBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
    }

    private fun setUpActions() {
        binding.btnConfirm.setOnClickListener {
            var calendar = Calendar.getInstance()
            calendar.set(
                binding.datePicker.year,
                binding.datePicker.month,
                binding.datePicker.dayOfMonth
            )
            onConfirm(calendar.time)
            dismiss()
        }
    }
}