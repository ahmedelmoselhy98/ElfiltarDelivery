package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.delivery.adapters.WaterQualityAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.WaterQualityModel
import com.elfiltar.elfiltartechnician.business.delivery.sheets.AddFilterCandleCount
import com.elfiltar.elfiltartechnician.business.delivery.sheets.WaterQualitySheet
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityFiltersBinding
import www.sanju.motiontoast.MotionToast

class FiltersActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityFiltersBinding
    lateinit var adapter: WaterQualityAdapter
    var dataList = ArrayList<WaterQualityModel>()
    private val appViewModel: AppViewModel by viewModels()
    override fun setUpLayoutView(): View {
        binding = ActivityFiltersBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
        getWaterQuality()
        setUpList()
    }

    private fun getWaterQuality() {
        appViewModel.getWaterQualitiesFilterSetting {
            if (it.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                return@getWaterQualitiesFilterSetting
            }
            binding.tvEmpty.visibility = View.GONE
            dataList.clear()
            dataList.addAll(it)
            adapter.replaceDataList(dataList)
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnDone.setOnClickListener {
            var map = HashMap<String, Any>()
            AddFilterCandleCount(this, map, onSubmit = {
                appViewModel.createCandle(map, onResult = {
                    MyUtils.shoMsg(this, getString(R.string.success), MotionToast.TOAST_SUCCESS)
                    getWaterQuality()
                })
            }).show()
        }
        binding.btnAdd.setOnClickListener {
            var map = HashMap<String, Any>()
            WaterQualitySheet(this, false, WaterQualityModel(), map, onSubmit = {
                appViewModel.createWaterQuality(map, onResult = {
                    getWaterQuality()
                })
            }).show()
        }
    }

    private fun setUpList() {
        adapter =
            WaterQualityAdapter(
                dataList,
                onWaterQualityOptionsClicked = { view, position, waterQuality ->
                    showWaterQualityOptionsPopup(waterQuality, view)
                },
                onWaterCandleOptionsClicked = { view, position, candle ->
                    var map = HashMap<String, Any>()
                    map["water_quality_id"] = candle.water_quality_id!!
                    map["candle_number"] = candle.candle_number!!
                    map["change_after"] = candle.change_after!!
                    appViewModel.updateCandle(candle.id!!, map, onResult = {
                        getWaterQuality()
                    })
                },
                onItemClicked = { position, orderModel ->
                })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }


    // Show Notification Options Popup
    private fun showWaterQualityOptionsPopup(waterQualityModel: WaterQualityModel, view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.water_quality_menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    var map = HashMap<String, Any>()
                    WaterQualitySheet(this, true, waterQualityModel, map, onSubmit = {
                        appViewModel.updateWaterQuality(waterQualityModel.id!!, map, onResult = {
                            getWaterQuality()
                        })
                    }).show()
                }
                R.id.menu_delete -> {
                    appViewModel.deleteWaterQuality(waterQualityModel.id!!, onResult = {
                        getWaterQuality()
                    })
                }
            }
            false
        }
    }
}