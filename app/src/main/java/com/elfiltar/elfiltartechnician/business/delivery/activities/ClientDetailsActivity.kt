package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.delivery.adapters.AlarmAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.AlarmModel
import com.elfiltar.elfiltartechnician.business.delivery.models.ClientModel
import com.elfiltar.elfiltartechnician.business.delivery.sheets.AddAlarmSheet
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityClientsClientDetailsBinding
import com.google.gson.Gson
import www.sanju.motiontoast.MotionToast

class ClientDetailsActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityClientsClientDetailsBinding
    lateinit var adapter: AlarmAdapter
    var dataList = ArrayList<AlarmModel>()
    var clientModel = ClientModel()
    private val appViewModel: AppViewModel by viewModels()
    var queryMap = HashMap<String, Any>()

    override fun setUpLayoutView(): View {
        binding = ActivityClientsClientDetailsBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpList()
        setUpPageActions()
        clientModel = Gson().fromJson(intent.getStringExtra("client"), ClientModel::class.java)
        binding.client = clientModel
        queryMap["technician_client_id"] = clientModel.id!!
        binding.tvLocation.text = if (clientModel.governorate == null) "${clientModel.address}"
        else "${clientModel.governorate!!.title} -${clientModel.city!!.title} - ${clientModel.address}"
        getAlarms()
    }

    private fun getAlarms() {
        appViewModel.getTechnicianAlarms(clientModel.id!!, onResult = {
            if (it.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                return@getTechnicianAlarms
            }
            binding.tvEmpty.visibility = View.GONE
            dataList.clear()
            dataList.addAll(it)
            adapter.replaceDataList(dataList)
            binding.recyclerView.visibility = View.VISIBLE
        })
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnAddAlarm.setOnClickListener {
            AddAlarmSheet(this, queryMap, onConfirm = {
                appViewModel.addTechnicianAlarm(queryMap, onResult = {
                    MyUtils.shoMsg(this, getString(R.string.success), MotionToast.TOAST_SUCCESS)
                    getAlarms()
                })
            }).show()
        }
        binding.btnWhatsApp.setOnClickListener {
            MyUtils.openWhatsApp(this, clientModel.phone!!)
        }
        binding.btnCall.setOnClickListener {
            MyUtils.callPhoneNumber(this, clientModel.phone!!)
        }
        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this, ClientsUpdateClientActivity::class.java).putExtra(
                    "client",
                    Gson().toJson(clientModel)
                )
            )
        }
    }

    private fun setUpList() {
        adapter = AlarmAdapter(dataList) { position, orderModel ->
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}