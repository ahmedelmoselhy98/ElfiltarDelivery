package com.elfiltar.elfiltartechnician.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutAddFilterCandleBinding

class AddFilterCandleCount(
    private val mContext: Context,
    private val queryMap: HashMap<String, Any>,
    val onSubmit: () -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutAddFilterCandleBinding =
        SheetLayoutAddFilterCandleBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
    }

    private fun setUpActions() {
        if ((mContext as BaseActivity).sessionHelper.getUserSession()!!.candles_count != null)
            binding.etCandleNumber.setText("" + (mContext as BaseActivity).sessionHelper.getUserSession()!!.candles_count!!)
        binding.btnSubmit.setOnClickListener {
            if (!binding.etCandleNumber.isValid) {
                binding.etCandleNumber.error = mContext.getString(R.string.error_message_required)
                return@setOnClickListener
            }
            queryMap["candles_count"] = binding.etCandleNumber.text.toString().toInt()
            onSubmit()
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}