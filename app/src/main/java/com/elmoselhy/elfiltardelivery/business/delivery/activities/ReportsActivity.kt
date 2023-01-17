package com.elmoselhy.elfiltardelivery.business.delivery.activities

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.business.delivery.adapters.PreviousOrdersAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.ClientModel
import com.elmoselhy.elfiltardelivery.business.delivery.models.OrderModel
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.elmoselhy.elfiltardelivery.databinding.ActivityPreviousOrdersBinding
import com.elmoselhy.elfiltardelivery.databinding.ActivityReportsBinding

class ReportsActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityReportsBinding
    private val appViewModel: AppViewModel by viewModels()
    override fun setUpLayoutView(): View {
        binding = ActivityReportsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        setUpPageActions()
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        appViewModel.technicianReport {
            binding.run {
                tvActiveClients.text = "${it.activeClients}"
                tvUnActiveClients.text = "${it.unActiveClients}"
                tvActiveMaintenanceClients.text = "${it.activeMaintenance}"
                tvUnActiveMaintenanceClients.text = "${it.unActiveMaintenance}"
                tvEarnings.text = "${it.earnings}"
                tvEndRequests.text = "${it.endRequests}"
                tvCurrentRequests.text = "${it.currentRequests}"
                tvCanceledRequests.text = "${it.canceledRequests}"
            }
        }

    }
}