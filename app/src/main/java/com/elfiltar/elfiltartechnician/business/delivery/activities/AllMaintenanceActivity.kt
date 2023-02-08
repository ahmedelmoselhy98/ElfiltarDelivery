package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.delivery.adapters.ClientAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.ClientModel
import com.elfiltar.elfiltartechnician.business.delivery.models.GovernorateWithCitiesModel
import com.elfiltar.elfiltartechnician.business.delivery.sheets.ClientsFilterSheet
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.databinding.ActivityAllClientsBinding
import com.google.gson.Gson

class AllMaintenanceActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityAllClientsBinding
    lateinit var adapter: ClientAdapter
    var dataList = ArrayList<ClientModel>()
    private val appViewModel: AppViewModel by viewModels()
    var clientsQuery = HashMap<String, Any>()
    var governorateWithCitiesList = ArrayList<GovernorateWithCitiesModel>()
    override fun setUpLayoutView(): View {
        binding = ActivityAllClientsBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpList()
        setUpPageActions()
        appViewModel.getGovernorateWithCities {
            governorateWithCitiesList.addAll(it)
        }
    }

    override fun onStart() {
        super.onStart()
        getClients()
    }

    private fun getClients() {
        appViewModel.getAllMaintenanceClients(clientsQuery, onResult = {
            updateDataList(it)
        })
    }

    private fun updateDataList(it: List<ClientModel>) {
        if (it.isNullOrEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
            return
        }
        binding.tvEmpty.visibility = View.GONE
        dataList.clear()
        dataList.addAll(it)
        adapter.replaceDataList(dataList)
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun setUpPageActions() {
        binding.etSearch.isRequired = false
        binding.etSearch.setOnTextTyping(object : BaseInput.TypingCallback {
            override fun onTyping(text: String) {
                updateDataList(dataList.filter {
                    it.phone!!.contains(text) || it.first_name!!.contains(text)
                            || it.last_name!!.contains(text)
                })
            }
        })
        binding.appBar.title = getString(R.string.maintenance)
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.ivFilter.setOnClickListener {
            ClientsFilterSheet(this, governorateWithCitiesList, clientsQuery, onConfirm = {
                getClients()
            }).show()
        }
    }

    private fun setUpList() {
        adapter = ClientAdapter(dataList) { position, model ->
            startActivity(
                Intent(this, MaintenanceDetailsActivity::class.java).putExtra(
                    "client", Gson().toJson(model)
                )
            )
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}