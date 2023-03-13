package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.authentication.activities.ProfileActivity
import com.elfiltar.elfiltartechnician.business.delivery.adapters.OrdersAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.OrderModel
import com.elfiltar.elfiltartechnician.business.delivery.sheets.CancelOrderSheet
import com.elfiltar.elfiltartechnician.business.delivery.sheets.PackageSubscriptionEndSheet
import com.elfiltar.elfiltartechnician.business.general.activities.ContactUsActivity
import com.elfiltar.elfiltartechnician.business.general.activities.NotificationActivity
import com.elfiltar.elfiltartechnician.business.general.activities.SplashActivity
import com.elfiltar.elfiltartechnician.business.general.sheets.LanguageSheet
import com.elfiltar.elfiltartechnician.business.general.sheets.LogoutDialog
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityMainBinding
    private val appViewModel: AppViewModel by viewModels()

    var dataList = ArrayList<OrderModel>()
    lateinit var adapter: OrdersAdapter

    override fun setUpLayoutView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpList()
        setUpPageActions()
    }

    private fun getProfile() {
        appViewModel.getTechnicianProfile {
            sessionHelper.setUserSession(it)
            binding.profile = it
            if (it.package_sub_end_days == 0) {
                PackageSubscriptionEndSheet(this, onConfirm = {
                    startActivity(Intent(this, PackagesActivity::class.java))
                }, onLogout = {
                    logout()
                }).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getOrderList()
        getProfile()
    }

    private fun getOrderList() {
        appViewModel.getOrders("current", onResult = {
            if (it.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                return@getOrders
            }
            binding.tvEmpty.visibility = View.GONE
            dataList.clear()
            dataList.addAll(it)
            adapter.replaceDataList(dataList)
            binding.recyclerView.visibility = View.VISIBLE
        })
    }

    private fun setUpPageActions() {
        setUpDrawerLayout()
        binding.tvSubscriptionPackages.setOnClickListener {
            startActivity(Intent(this, PackagesActivity::class.java))
        }
        binding.ivNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        binding.btnControlMyClients.setOnClickListener {
            startActivity(Intent(this, ClientsActivity::class.java))
        }
        binding.btnControlMaintenance.setOnClickListener {
            startActivity(Intent(this, MaintenanceActivity::class.java))
        }
    }

    private fun setUpList() {
        adapter = OrdersAdapter(dataList, onDetailsClicked = { position, item ->
            startActivity(Intent(this, OrderDetailsActivity::class.java).putExtra("id", item.id))
        }, onRejectClicked = { position, orderModel ->
            CancelOrderSheet(this) {
                appViewModel.cancelOrder(orderModel.id!!, it, onResult = {
                    getOrderList()
                })
            }.show()
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpDrawerLayout() {
        binding.ivMenu.setOnClickListener {
            if (binding.drawerLayout.isOpen) binding.drawerLayout.close()
            else binding.drawerLayout.open()
        }
        binding.tvAccountSettings.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.tvLanguage.setOnClickListener {
            LanguageSheet(this).show()
        }
        binding.tvFilters.setOnClickListener {
            startActivity(Intent(this, FiltersActivity::class.java))
        }
        binding.tvPackages.setOnClickListener {
            startActivity(Intent(this, PackagesActivity::class.java))
        }
        binding.tvReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }
        binding.tvContactUs.setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }
        binding.tvPreviousOrders.setOnClickListener {
            startActivity(Intent(this, PreviousOrdersActivity::class.java))
        }
        binding.tvDevelopedBy.setOnClickListener {
            MyUtils.openUrl(this, "https://www.blue-egypt.com/ar ")
        }
        binding.tvLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        LogoutDialog(this, onConfirm = {
            sessionHelper.clearUserSession(this)
            startActivity(Intent(this, SplashActivity::class.java))
            finishAffinity()
        }).show()
    }


}