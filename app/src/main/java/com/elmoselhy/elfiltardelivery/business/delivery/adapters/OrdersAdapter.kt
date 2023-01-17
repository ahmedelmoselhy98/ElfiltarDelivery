package com.elmoselhy.elfiltardelivery.business.delivery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.base.BaseAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.OrderModel
import com.elmoselhy.elfiltardelivery.commons.helpers.MyConstants
import com.elmoselhy.elfiltardelivery.databinding.ItemLayoutOrderBinding

class OrdersAdapter(
    var itemsList: ArrayList<OrderModel>,
    private val onDetailsClicked: (Int, OrderModel) -> Unit,
    private val onRejectClicked: (Int, OrderModel) -> Unit
) :
    BaseAdapter<OrdersAdapter.CustomViewHolder, OrderModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutOrderBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    //onBindViewHolder
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        setUpData(holder, position)
        setUpActions(holder, position)
    }

    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.item = itemsList[position]
        if (itemsList[position].status == MyConstants.Enums.OrderStatus.new)
            holder.binding.btnReject.visibility = View.VISIBLE
        else holder.binding.btnReject.visibility = View.GONE

        if (itemsList[position].user!!.country != null && itemsList[position].user!!.governorate != null &&
            itemsList[position].user!!.address != null
        )
            holder.binding.tvAddress.text =
                "${itemsList[position].user!!.country!!.title} - ${itemsList[position].user!!.governorate!!.title} - ${itemsList[position].user!!.address}"
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
        holder.binding.btnDetails.setOnClickListener {
            onDetailsClicked(position, itemsList[position])
        }
        holder.binding.btnReject.setOnClickListener {
            onRejectClicked(position, itemsList[position])
        }
    }

}