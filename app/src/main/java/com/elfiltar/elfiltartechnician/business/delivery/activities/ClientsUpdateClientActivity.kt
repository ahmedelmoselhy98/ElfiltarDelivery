package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.business.delivery.models.ClientModel
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityClientsUpdateClientBinding
import com.google.gson.Gson
import www.sanju.motiontoast.MotionToast

class ClientsUpdateClientActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityClientsUpdateClientBinding
    private val appViewModel: AppViewModel by viewModels()
    var bodyMap = HashMap<String, Any>()
    var clientModel = ClientModel()

    override fun setUpLayoutView(): View {
        binding = ActivityClientsUpdateClientBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        clientModel = Gson().fromJson(intent!!.getStringExtra("client"), ClientModel::class.java)
        binding.item = clientModel
        if (clientModel.id != null)
            bodyMap["client_id"] = clientModel.id!!

        bodyMap["city_id"] = clientModel.city_id!!
        bodyMap["governorate_id"] = clientModel.governorate_id!!

        setUpPageActions()
        setUpGovernorateSelection()

        MyUtils.executeDelay(500, onExecute = {
            binding.includeSelectionCity.tvTitle.text = "" + clientModel.city!!.title!!
            binding.includeSelectionGovernorate.tvTitle.text =
                "" + clientModel.governorate!!.title!!
        })
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            if (ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList())) {
                addClient()
            }
        }

    }

    private fun addClient() {
        bodyMap["first_name"] = binding.etFirstName.text.toString()
        bodyMap["last_name"] = binding.etLastName.text.toString()
        bodyMap["address"] = binding.etLocation.text.toString()
        bodyMap["phone_code"] = binding.countryCodePicker.selectedCountryCode
        bodyMap["phone"] = binding.etPhone.text.toString()
        bodyMap["notes"] = binding.etNotes.text.toString()
        if (binding.tvDateOfContract.isValid)
            bodyMap["date_of_contract"] = binding.tvDateOfContract.apiDate
        bodyMap["status"] = if (binding.switchStatus.isChecked) 1 else 0
        appViewModel.updateClient(bodyMap, onResult = {
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
        inputsList.add(binding.etPhone)
        inputsList.add(binding.etLocation)
        inputsList.add(binding.etNotes)
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