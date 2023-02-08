package com.elfiltar.elfiltartechnician.business.delivery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elfiltar.elfiltartechnician.base.BaseAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.ClientModel
import com.elfiltar.elfiltartechnician.databinding.ItemLayoutClientBinding

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