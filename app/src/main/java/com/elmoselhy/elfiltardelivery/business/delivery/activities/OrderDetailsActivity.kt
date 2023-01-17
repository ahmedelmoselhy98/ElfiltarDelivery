package com.elmoselhy.elfiltardelivery.business.delivery.activities

import android.view.View
import androidx.activity.viewModels
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.business.delivery.models.OrderModel
import com.elmoselhy.elfiltardelivery.business.delivery.sheets.CancelOrderSheet
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elmoselhy.elfiltardelivery.commons.helpers.MyConstants
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.ActivityOrderDetailsBinding
import www.sanju.motiontoast.MotionToast

class OrderDetailsActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityOrderDetailsBinding
    private val appViewModel: AppViewModel by viewModels()

    var orderDetails = OrderModel()
    override fun setUpLayoutView(): View {
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
        getOrderDetails()
    }

    private fun getOrderDetails() {
        appViewModel.getOrderDetails(intent!!.getIntExtra("id", 0), onResult = {
            if (it == null) {
                MyUtils.shoMsg(this, "Error in DataModel", MotionToast.TOAST_ERROR)
                onBackPressed()
            }
            orderDetails = it
            binding.tvName.text = "${it.user!!.first_name} ${it.user!!.last_name}"

            if (it.user!!.country != null && it.user!!.governorate != null &&
                it.user!!.address != null
            )
                binding.tvAddress.text =
                    "${it.user!!.country} - ${it.user!!.governorate} - ${it.user!!.address}"

            binding.sectionContacts.visibility = View.GONE
            binding.sectionPendingActions.visibility = View.GONE
            binding.sectionCost.visibility = View.GONE
            binding.btnFinish.visibility = View.GONE
            binding.btnOnTheWay.visibility = View.GONE
            binding.tvOrderCanceled.visibility = View.GONE
            binding.sectionCancelReason.visibility = View.GONE


            when (orderDetails.status) {
                MyConstants.Enums.OrderStatus.end -> {
                    binding.tvOrderCanceled.text = getString(R.string.order_ended)
                    binding.tvOrderCanceled.visibility = View.VISIBLE
                    binding.ivStatusImage.setImageResource(R.drawable.image_order_details1)
                }
                MyConstants.Enums.OrderStatus.cancel -> {
                    binding.tvOrderCanceled.text = getString(R.string.order_canceled)
                    binding.tvOrderCanceled.visibility = View.VISIBLE
                    binding.sectionCancelReason.visibility = View.VISIBLE
                    binding.ivStatusImage.setImageResource(R.drawable.image_order_details1)
                }
                MyConstants.Enums.OrderStatus.new -> {
                    binding.sectionPendingActions.visibility = View.VISIBLE
                    binding.ivStatusImage.setImageResource(R.drawable.image_order_details1)
                }
                MyConstants.Enums.OrderStatus.confirmed -> {
                    binding.sectionContacts.visibility = View.VISIBLE
                    binding.btnOnTheWay.visibility = View.VISIBLE
                    binding.ivStatusImage.setImageResource(R.drawable.image_order_details2)
                }
                MyConstants.Enums.OrderStatus.on_way -> {
                    binding.sectionCost.visibility = View.VISIBLE
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.ivStatusImage.setImageResource(R.drawable.image_order_details3)
                }
            }
            binding.item = orderDetails
        })
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnWhatsApp.setOnClickListener {
            MyUtils.openWhatsApp(this, orderDetails.user!!.phone!!)
        }
        binding.btnCall.setOnClickListener {
            MyUtils.callPhoneNumber(this, orderDetails.user!!.phone!!)
        }
        binding.btnAccept.setOnClickListener {
            appViewModel.acceptOrder(intent!!.getIntExtra("id", 0), onResult = {
                getOrderDetails()
            })
        }
        binding.btnReject.setOnClickListener {
            CancelOrderSheet(this) {
                appViewModel.cancelOrder(intent!!.getIntExtra("id", 0), it, onResult = {
                    getOrderDetails()
                })
            }.show()
        }
        binding.btnOnTheWay.setOnClickListener {
            appViewModel.orderOnTheWay(intent!!.getIntExtra("id", 0), onResult = {
                getOrderDetails()
            })
        }
        binding.btnFinish.setOnClickListener {
            if (ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList()))
                appViewModel.finishOrder(intent!!.getIntExtra("id", 0),
                    binding.etCost.text.toString().toDouble(), onResult = {
                        finish()
                    })
        }
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etCost)
        return inputsList
    }
}