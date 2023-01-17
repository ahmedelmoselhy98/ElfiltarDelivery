package com.elmoselhy.elfiltardelivery.business.delivery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.base.BaseAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.OrderModel
import com.elmoselhy.elfiltardelivery.databinding.ItemLayoutPreviousOrderBinding

class PreviousOrdersAdapter(
    var itemsList: ArrayList<OrderModel>,
    private val onItemClicked: (Int, OrderModel) -> Unit,
) :
    BaseAdapter<PreviousOrdersAdapter.CustomViewHolder, OrderModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutPreviousOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutPreviousOrderBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    //onBindViewHolder
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        setUpData(holder, position)
        setUpActions(holder, position)
    }

    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.item = itemsList[position]
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(position, itemsList[position])
        }
    }

}