package com.elfiltar.elfiltartechnician.business.general.activities

import android.view.View
import androidx.activity.viewModels
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityContactUsBinding
import www.sanju.motiontoast.MotionToast

class ContactUsActivity : BaseActivity() {

    //declare properties
    lateinit var binding: ActivityContactUsBinding
    private val appViewModel: AppViewModel by viewModels()
    val map = HashMap<String, Any>()
    override fun setUpLayoutView(): View {
        binding = ActivityContactUsBinding.inflate(layoutInflater)
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
            if (ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList())) {
                contactUs()
            }
        }
    }

    private fun contactUs() {
        map["message"] = binding.etMessage.text.toString()
        appViewModel.contactUs(map, onResult = {
            MyUtils.shoMsg(this,getString(R.string.success),MotionToast.TOAST_SUCCESS)
            finish()
        })
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etMessage)
        return inputsList
    }
}