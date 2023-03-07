package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.view.View
import androidx.activity.viewModels
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityMaintenanceAddClientBinding
import www.sanju.motiontoast.MotionToast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MaintenanceAddClientActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityMaintenanceAddClientBinding
    private val appViewModel: AppViewModel by viewModels()
    var bodyMap = HashMap<String, Any>()

    override fun setUpLayoutView(): View {
        binding = ActivityMaintenanceAddClientBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
        setUpGovernorateSelection()
        setUpWaterSelection()
        setUpStagesSelection()
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            binding.btnAdd.setOnClickListener {
                if (!ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList())) {
                    return@setOnClickListener
                }
                if (bodyMap["governorate_id"] == null) {
                    MyUtils.shoMsg(
                        this,
                        getString(R.string.governorate) + " " + getString(R.string.error_message_required),
                        MotionToast.TOAST_ERROR
                    )
                    return@setOnClickListener
                }
                if (bodyMap["city_id"] == null) {
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
        binding.cbDate.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (compoundButton.isPressed)
                if (isChecked)
                    resetDates()
                else {
                    setDatesCurrent()
                }
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
        else {
            MyUtils.shoMsg(
                this,
                getString(R.string.date_of_contract) + " " + getString(R.string.error_message_required),
                MotionToast.TOAST_ERROR
            )
            return
        }

        bodyMap["status"] = if (binding.switchStatus.isChecked) 1 else 0

        var datesList = ArrayList<String>()
        if (binding.tvDate1.isValid)
            datesList.add(binding.tvDate1.apiDate)
        if (binding.tvDate2.isValid)
            datesList.add(binding.tvDate2.apiDate)
        if (binding.tvDate3.isValid)
            datesList.add(binding.tvDate3.apiDate)
        if (binding.tvDate4.isValid)
            datesList.add(binding.tvDate4.apiDate)
        if (binding.tvDate5.isValid)
            datesList.add(binding.tvDate5.apiDate)
        if (binding.tvDate6.isValid)
            datesList.add(binding.tvDate6.apiDate)
        if (binding.tvDate7.isValid)
            datesList.add(binding.tvDate7.apiDate)

        if (!datesList.isNullOrEmpty()) {
            bodyMap["dates"] = datesList
        } else MyUtils.shoMsg(
            this,
            getString(R.string.choose_changes_dates),
            MotionToast.TOAST_ERROR
        )

        bodyMap["is_remember_dates"] =
            if (binding.cbDate.isChecked) 1 else 0

        appViewModel.addMaintenanceClient(bodyMap, onResult = {
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

    private fun setUpWaterSelection() {
        appViewModel.getWaterQualities(onResult = { list ->
            UtilsCustomExpandable.setUpExpandList(
                this,
                binding.includeSelectionWaterQuality.recyclerView,
                list,
                binding.includeSelectionWaterQuality.viewExpand,
                getString(R.string.water_quality),
                binding.includeSelectionWaterQuality.tvTitle,
                binding.includeSelectionWaterQuality.tvEmpty,
                binding.includeSelectionWaterQuality.etSearch,
                0,
                onItemSelected = { position, item ->
                    bodyMap["water_quality_id"] = list[position].id!!
                },
                onItemUnSelected = { position, item ->
                    bodyMap.remove("water_quality_id")
                }
            )
        })
    }

    private fun setUpStagesSelection() {
        val list = getStagesNumberList()
        UtilsCustomExpandable.setUpExpandList(
            this,
            binding.includeSelectionNumberOfStages.recyclerView,
            list,
            binding.includeSelectionNumberOfStages.viewExpand,
            getString(R.string.number_of_stages),
            binding.includeSelectionNumberOfStages.tvTitle,
            binding.includeSelectionNumberOfStages.tvEmpty,
            binding.includeSelectionNumberOfStages.etSearch,
            0,
            onItemSelected = { position, item ->
                binding.numberOfStages = item.id
                bodyMap["stages_number"] = list[position].id!!
                binding.sectionCandle.visibility = View.VISIBLE
                setDatesCurrent()
            },
            onItemUnSelected = { position, item ->
                binding.numberOfStages = 0
                bodyMap.remove("stages_number")
                binding.sectionCandle.visibility = View.GONE
                resetDates()
            }
        )
    }


    fun setDatesCurrent() {
        bodyMap["stages_number"]?.let {
            if (it.toString().toInt() >= 1)
                binding.tvDate1.setDates(Date())
            if (it.toString().toInt() >= 2)
                binding.tvDate2.setDates(Date())
            if (it.toString().toInt() >= 3)
                binding.tvDate3.setDates(Date())
            if (it.toString().toInt() >= 4)
                binding.tvDate4.setDates(Date())
            if (it.toString().toInt() >= 5)
                binding.tvDate5.setDates(Date())
            if (it.toString().toInt() >= 6)
                binding.tvDate6.setDates(Date())
            if (it.toString().toInt() >= 7)
                binding.tvDate7.setDates(Date())
        }
    }

    fun resetDates() {
        binding.tvDate1.reset()
        binding.tvDate2.reset()
        binding.tvDate3.reset()
        binding.tvDate4.reset()
        binding.tvDate5.reset()
        binding.tvDate6.reset()
        binding.tvDate7.reset()
        binding.tvDate7.reset()
    }

    private fun getStagesNumberList(): ArrayList<BaseModel> {
        var list = ArrayList<BaseModel>()
        if (sessionHelper.getUserSession()!!.candles_count != null && sessionHelper.getUserSession()!!.candles_count!! > 0)
            for (position in 1..sessionHelper.getUserSession()!!.candles_count!!) {
                val stage = BaseModel()
                stage.id = position
                stage.title = "" + position
                list.add(stage)
            }
        return list
    }

}