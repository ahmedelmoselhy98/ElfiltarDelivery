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
import com.elfiltar.elfiltartechnician.databinding.ActivityMaintenanceUpdateClientBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sheet_layout_otp.*
import www.sanju.motiontoast.MotionToast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MaintenanceUpdateClientActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityMaintenanceUpdateClientBinding
    private val appViewModel: AppViewModel by viewModels()
    var bodyMap = HashMap<String, Any>()
    var clientModel = ClientModel()

    override fun setUpLayoutView(): View {
        binding = ActivityMaintenanceUpdateClientBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        clientModel = Gson().fromJson(intent!!.getStringExtra("client"), ClientModel::class.java)
        binding.item = clientModel
        if (clientModel.id != null)
            bodyMap["client_id"] = clientModel.id!!
        bodyMap["stages_number"] = clientModel.stages_number!!
        binding.numberOfStages = clientModel.stages_number!!
        bodyMap["city_id"] = clientModel.city_id!!
        bodyMap["governorate_id"] = clientModel.governorate_id!!
        bodyMap["water_quality_id"] = clientModel.water_quality!!.id!!

        clientModel

        MyUtils.executeDelay(500, onExecute = {
            binding.includeSelectionNumberOfStages.tvTitle.text = "" + clientModel.stages_number!!
            binding.includeSelectionWaterQuality.tvTitle.text =
                "" + clientModel.water_quality!!.title!!
            binding.includeSelectionCity.tvTitle.text = "" + clientModel.city!!.title!!
            binding.includeSelectionGovernorate.tvTitle.text =
                "" + clientModel.governorate!!.title!!
        })
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
                if (ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList())) {
                    addClient()
                }
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
        bodyMap["date_of_contract"] = binding.tvDateOfContract.apiDate
        if (binding.tvDateOfContract.isValid)
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
        appViewModel.updateMaintenanceClient(bodyMap, onResult = {
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
    fun getDatesView(): ArrayList<TextView>{
        var viewList = ArrayList<TextView>()
        viewList.add(binding.tvDate1)
        viewList.add(binding.tvDate2)
        viewList.add(binding.tvDate3)
        viewList.add(binding.tvDate4)
        viewList.add(binding.tvDate5)
        viewList.add(binding.tvDate6)
        viewList.add(binding.tvDate7)
        return viewList
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