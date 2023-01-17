package com.elmoselhy.elfiltardelivery.business.delivery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.ClientModel
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.ItemLayoutClientBinding

class ClientAdapter(
    var itemsList: ArrayList<ClientModel>,
    private val onDetailsClicked: (Int, ClientModel) -> Unit,
) :
    BaseAdapter<ClientAdapter.CustomViewHolder, ClientModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutClientBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutClientBinding.inflate(inflater, parent, false)
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
        holder.binding.tvDetails.setOnClickListener {
            onDetailsClicked(position, itemsList[position])
        }
    }

}