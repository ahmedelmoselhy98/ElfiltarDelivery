package com.elmoselhy.elfiltardelivery.business.delivery.activities

import android.view.View
import androidx.activity.viewModels
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.business.delivery.models.WaterQualityModel
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.elmoselhy.elfiltardelivery.databinding.ActivityMaintenanceAddClientBinding
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
                    binding.sectionCandle.visibility = View.VISIBLE
                else {
                    binding.sectionCandle.visibility = View.GONE
                }
        }
    }

    private fun addClient() {
        bodyMap["first_name"] = binding.etFirstName.text.toString()
        bodyMap["last_name"] = binding.etLastName.text.toString()
        bodyMap["address"] = binding.etLocation.text.toString()
        bodyMap["phone_code"] = binding.countryCodePicker.selectedCountryCode
        bodyMap["phone"] = binding.etPhone.text.toString()
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

        if (binding.cbDate.isChecked) {

            if (!datesList.isNullOrEmpty()) {
                bodyMap["is_remember_dates"] = 1
                bodyMap["dates"] = datesList
            } else MyUtils.shoMsg(
                this,
                getString(R.string.choose_changes_dates),
                MotionToast.TOAST_ERROR
            )
        } else {
            datesList.add(MyUtils.formatDate(Date()))
            bodyMap["is_remember_dates"] = 0
            bodyMap["dates"] = datesList
        }
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
            },
            onItemUnSelected = { position, item ->
                binding.numberOfStages = 0
                bodyMap.remove("stages_number")
            }
        )
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