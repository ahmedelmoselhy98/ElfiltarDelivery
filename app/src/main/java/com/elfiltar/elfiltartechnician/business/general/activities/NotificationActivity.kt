package com.elfiltar.elfiltartechnician.business.general.activities

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.general.adapters.NotificationsAdapter
import com.elfiltar.elfiltartechnician.business.general.models.NotificationModel
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityNotificationsBinding
import www.sanju.motiontoast.MotionToast

class NotificationActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityNotificationsBinding
    lateinit var adapter: NotificationsAdapter
    var dataList = ArrayList<NotificationModel>()
    private val appViewModel: AppViewModel by viewModels()

    override fun setUpLayoutView(): View {
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
        getNotifications()
        setUpNotificationsList()
    }

    private fun getNotifications() {
        appViewModel.getNotifications {
            if (it.isNullOrEmpty()) {
                binding.ivDeleteNotifications.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                return@getNotifications
            }
            binding.ivDeleteNotifications.visibility = View.VISIBLE
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
        binding.ivDeleteNotifications.setOnClickListener {
            if (dataList.isNullOrEmpty()) {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.no_notifications_found),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            }
            appViewModel.deleteAllNotifications {
                MyUtils.shoMsg(this, getString(R.string.success), MotionToast.TOAST_SUCCESS)
            }
        }
    }

    private fun setUpNotificationsList() {
        adapter = NotificationsAdapter(dataList) { position, model -> }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}