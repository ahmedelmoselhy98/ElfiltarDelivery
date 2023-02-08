package com.elfiltar.elfiltartechnician.business.general.sheets

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.business.general.activities.SplashActivity
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutLanguageBinding

class LanguageSheet(
    private val mContext: Context
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutLanguageBinding =
        SheetLayoutLanguageBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
    }

    private fun setUpActions() {
        var sessionHelper = (mContext as BaseActivity).sessionHelper

        var lang = sessionHelper.getUserLanguage(mContext)
        when (lang) {
            sessionHelper.ENGLISH -> {
                binding.cbEnglish.isChecked = true
            }
            sessionHelper.ARABIC -> {
                binding.cbArabic.isChecked = true
            }
        }
        binding.cbEnglish.setOnClickListener {
            binding.sectionEnglish.performClick()
        }
        binding.cbArabic.setOnClickListener {
            binding.sectionArabic.performClick()
        }
        binding.sectionArabic.setOnClickListener {
            binding.cbArabic.isChecked = true
            binding.cbEnglish.isChecked = false
            lang = sessionHelper.ARABIC
        }
        binding.sectionEnglish.setOnClickListener {
            binding.cbArabic.isChecked = false
            binding.cbEnglish.isChecked = true
            lang = sessionHelper.ENGLISH
        }
        binding.btnDone.setOnClickListener {
            sessionHelper.setUserLanguageSession(lang) {
                dismiss()
                mContext.finishAffinity()
                mContext.startActivity(
                    Intent(
                        mContext,
                        SplashActivity::class.java
                    )
                )
            }
        }
    }
}