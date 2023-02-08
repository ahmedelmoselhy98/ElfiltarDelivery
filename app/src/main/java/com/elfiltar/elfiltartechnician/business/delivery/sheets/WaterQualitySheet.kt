package com.elfiltar.elfiltartechnician.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.business.delivery.models.WaterQualityModel
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutAddWaterQualityBinding

class WaterQualitySheet(
    private val mContext: Context,
    private val isUpdate: Boolean,
    private val waterQualityModel: WaterQualityModel,
    private val queryMap: HashMap<String, Any>,
    val onSubmit: () -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutAddWaterQualityBinding =
        SheetLayoutAddWaterQualityBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
        if (isUpdate) {
            if (!waterQualityModel.title_ar.isNullOrEmpty())
                binding.etNameAr.setText(waterQualityModel.title_ar!!)
            if (!waterQualityModel.title_en.isNullOrEmpty())
                binding.etNameEn.setText(waterQualityModel.title_en!!)
        }
    }

    private fun setUpActions() {
        binding.btnSubmit.setOnClickListener {
            if (!binding.etNameAr.isValid) {
                binding.etNameAr.error = mContext.getString(R.string.error_message_required)
                return@setOnClickListener
            }
            if (!binding.etNameEn.isValid) {
                binding.etNameEn.error = mContext.getString(R.string.error_message_required)
                return@setOnClickListener
            }
            queryMap["title_ar"] = binding.etNameAr.text.toString()
            queryMap["title_en"] = binding.etNameEn.text.toString()
            onSubmit()
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}