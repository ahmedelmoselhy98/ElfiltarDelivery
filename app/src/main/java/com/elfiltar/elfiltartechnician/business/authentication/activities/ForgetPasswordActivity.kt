package com.elfiltar.elfiltartechnician.business.authentication.activities

import android.view.View
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.databinding.ActivityForgetPasswordBinding

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