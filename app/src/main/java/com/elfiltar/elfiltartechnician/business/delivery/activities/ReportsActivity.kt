package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.view.View
import androidx.activity.viewModels
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.databinding.ActivityReportsBinding

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