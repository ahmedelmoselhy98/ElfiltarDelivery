package com.elfiltar.elfiltartechnician.business.delivery.sheets

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.base.BaseSheetDialog
import com.elfiltar.elfiltartechnician.business.delivery.models.GovernorateWithCitiesModel
import com.elfiltar.elfiltartechnician.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elfiltar.elfiltartechnician.databinding.SheetLayoutClientsFilterBinding

class ClientsFilterSheet(
    private val mContext: Context,
    private val governorateWithCitiesList: ArrayList<GovernorateWithCitiesModel>,
    private val map: HashMap<String, Any>,
    val onConfirm: () -> Unit,
) : BaseSheetDialog(mContext) {
    var binding: SheetLayoutClientsFilterBinding =
        SheetLayoutClientsFilterBinding.inflate(LayoutInflater.from(mContext))

    init {
        setContentView(binding.root)
        setUpSheetUi(binding.root.parent)
        setUpActions()
        setUpGovernorateSelection()
    }

    private fun setUpActions() {
        map["status"] = 1
        binding.switchStatus.setOnCheckedChangeListener { compoundButton, icEnabled ->
            if (compoundButton.isPressed) {
                if (icEnabled)
                    map["status"] = 1
                else
                    map["status"] = 0
            }
        }
        binding.btnConfirm.setOnClickListener {
            if (binding.tvFrom.isValid)
                map["fromDate"] = binding.tvFrom.apiDate
            if (binding.tvFrom.isValid)
                map["toDate"] = binding.tvTo.apiDate

            onConfirm()
            dismiss()
        }
    }

    private fun setUpGovernorateSelection() {
        UtilsCustomExpandable.setUpExpandList(
            mContext,
            binding.includeSelectionGovernorate.recyclerView,
            governorateWithCitiesList,
            binding.includeSelectionGovernorate.viewExpand,
            mContext.getString(R.string.governorate),
            binding.includeSelectionGovernorate.tvTitle,
            binding.includeSelectionGovernorate.tvEmpty,
            binding.includeSelectionGovernorate.etSearch,
            0,
            onItemSelected = { position, item ->
                map["governorate_id"] = governorateWithCitiesList[position].id!!
                if (!governorateWithCitiesList[position].cities.isNullOrEmpty())
                    setUpCitySelection(governorateWithCitiesList[position].cities!!)
            },
            onItemUnSelected = { position, item ->
                map.remove("governorate_id")
                setUpCitySelection(ArrayList())
            }
        )
    }

    private fun setUpCitySelection(list: ArrayList<BaseModel>) {
        UtilsCustomExpandable.setUpExpandList(
            mContext,
            binding.includeSelectionCity.recyclerView,
            list,
            binding.includeSelectionCity.viewExpand,
            mContext.getString(R.string.city),
            binding.includeSelectionCity.tvTitle,
            binding.includeSelectionCity.tvEmpty,
            binding.includeSelectionCity.etSearch,
            0,
            onItemSelected = { position, item ->
                map["city_id"] = list[position].id!!
            },
            onItemUnSelected = { position, item ->
                map.remove("city_id")
            }
        )
    }
}