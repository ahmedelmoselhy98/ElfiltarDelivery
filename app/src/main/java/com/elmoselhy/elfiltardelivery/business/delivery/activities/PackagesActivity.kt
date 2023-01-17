package com.elmoselhy.elfiltardelivery.business.delivery.activities

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.business.delivery.adapters.PackageAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.PackageModel
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.ActivityPackagesBinding
import www.sanju.motiontoast.MotionToast

class PackagesActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityPackagesBinding
    private val appViewModel: AppViewModel by viewModels()

    lateinit var adapter: PackageAdapter
    var dataList = ArrayList<PackageModel>()
    var packageId = -1
    override fun setUpLayoutView(): View {
        binding = ActivityPackagesBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpList()
        setUpPageActions()
        getPackages()
    }

    private fun getPackages() {
        appViewModel.getPackages {
            if (it.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                return@getPackages
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
        binding.btnSubscribe.setOnClickListener {
            if (packageId == -1) {
                MyUtils.shoMsg(this, getString(R.string.choose_package), MotionToast.TOAST_ERROR)
                return@setOnClickListener
            }
            appViewModel.subscribePackage(packageId,
                onResult = {
                    if (it != null) {
                        startActivity(Intent(this, PaymentActivity::class.java).putExtra("url", it))
                        finish()
                    }
                })
        }
    }

    private fun setUpList() {
        adapter = PackageAdapter(dataList) { position, item ->
            packageId = item.id!!
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}