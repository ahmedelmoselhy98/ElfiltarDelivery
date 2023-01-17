package com.elmoselhy.elfiltardelivery.business.authentication.activities

import android.content.Intent
import android.view.View
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.business.authentication.sheet.OtpSheet
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elmoselhy.elfiltardelivery.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityForgetPasswordBinding

    override fun setUpLayoutView(): View {
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        setUpPageActions()
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSend.setOnClickListener {

        }
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etPhone)
        return inputsList
    }
}