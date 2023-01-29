package com.elmoselhy.elfiltardelivery.business.delivery.activities

import android.view.View
import androidx.activity.viewModels
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.business.delivery.models.GovernorateWithCitiesModel
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.elmoselhy.elfiltardelivery.databinding.ActivityClientsAddClientBinding
import www.sanju.motiontoast.MotionToast

class ClientsAddClientActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityClientsAddClientBinding
    private val appViewModel: AppViewModel by viewModels()
    var bodyMap = HashMap<String, Any>()

    override fun setUpLayoutView(): View {
        binding = ActivityClientsAddClientBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
        setUpGovernorateSelection()
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            if (!ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList())) {
                return@setOnClickListener
            } else if (bodyMap["date_of_contract"] == null) {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.date_of_contract) + " " + getString(R.string.error_message_required),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            } else if (bodyMap["governorate_id"] == null) {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.governorate) + " " + getString(R.string.error_message_required),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            } else if (bodyMap["city_id"] == null) {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.city) + " " + getString(R.string.error_message_required),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            }
            addClient()
        }
    }

    private fun addClient() {
        bodyMap["first_name"] = binding.etFirstName.text.toString()
        bodyMap["last_name"] = binding.etLastName.text.toString()
        bodyMap["address"] = binding.etLocation.text.toString()
        bodyMap["phone_code"] = binding.countryCodePicker.selectedCountryCode
        bodyMap["phone"] = binding.etPhone.text.toString()
        if (binding.tvDateOfContract.isValid)
            bodyMap["date_of_contract"] = binding.tvDateOfContract.apiDate
        bodyMap["status"] = if (binding.switchStatus.isChecked) 1 else 0
        appViewModel.addClient(bodyMap, onResult = {
            MyUtils.shoMsg(
                this,
                getString(R.string.success),
                MotionToast.TOAST_SUCCESS
            )
            finish()
        })
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etFirstName)
        inputsList.add(binding.etLastName)
        inputsList.add(binding.etLocation)
        inputsList.add(binding.etPhone)
        return inputsList
    }

    private fun setUpGovernorateSelection() {
        appViewModel.getGovernorateWithCities(onResult = { list ->
            UtilsCustomExpandable.setUpExpandList(
                this,
                binding.includeSelectionGovernorate.recyclerView,
                list,
                binding.includeSelectionGovernorate.viewExpand,
                getString(R.string.governorate),
                binding.includeSelectionGovernorate.tvTitle,
                binding.includeSelectionGovernorate.tvEmpty,
                binding.includeSelectionGovernorate.etSearch,
                0,
                onItemSelected = { position, item ->
                    bodyMap["governorate_id"] = list[position].id!!
                    if (!list[position].cities.isNullOrEmpty())
                        setUpCitySelection(list[position].cities!!)
                },
                onItemUnSelected = { position, item ->
                    bodyMap.remove("governorate_id")
                    setUpCitySelection(ArrayList())
                }
            )
        })
    }

    private fun setUpCitySelection(list: ArrayList<BaseModel>) {
        UtilsCustomExpandable.setUpExpandList(
            this,
            binding.includeSelectionCity.recyclerView,
            list,
            binding.includeSelectionCity.viewExpand,
            getString(R.string.city),
            binding.includeSelectionCity.tvTitle,
            binding.includeSelectionCity.tvEmpty,
            binding.includeSelectionCity.etSearch,
            0,
            onItemSelected = { position, item ->
                bodyMap["city_id"] = list[position].id!!
            },
            onItemUnSelected = { position, item ->
                bodyMap.remove("city_id")
            }
        )
    }
}