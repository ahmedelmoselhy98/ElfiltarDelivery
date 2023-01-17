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

class PreviousOrdersActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityPreviousOrdersBinding
    lateinit var adapter: PreviousOrdersAdapter
    var dataList = ArrayList<OrderModel>()
    private val appViewModel: AppViewModel by viewModels()
    override fun setUpLayoutView(): View {
        binding = ActivityPreviousOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        setUpList()
        setUpPageActions()
        getOrderList()
    }

    private fun getOrderList() {
        appViewModel.getOrders("previous", onResult = {
            updateDataList(it)
        })
    }
    private fun updateDataList(it: List<OrderModel>) {
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
                    it.user!!.phone!!.contains(text) || it.user!!.first_name!!.contains(text)
                            || it.user!!.last_name!!.contains(text)
                })
            }
        })
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUpList() {
        adapter = PreviousOrdersAdapter(dataList) { position, item ->
            startActivity(Intent(this, OrderDetailsActivity::class.java).putExtra("id", item.id))
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}