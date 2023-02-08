package com.elfiltar.elfiltartechnician.business.authentication.activities

import android.content.Intent
import android.view.View
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.delivery.activities.MainActivity
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elfiltar.elfiltartechnician.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityResetPasswordBinding

    override fun setUpLayoutView(): View {
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        setUpPageActions()
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnConfirm.setOnClickListener {
            if (ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList()))
                startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etPassword)
        inputsList.add(binding.etConfirmPassword)
        return inputsList
    }
}