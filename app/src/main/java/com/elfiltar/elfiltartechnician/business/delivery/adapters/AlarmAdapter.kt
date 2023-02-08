package com.elfiltar.elfiltartechnician.business.delivery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elfiltar.elfiltartechnician.base.BaseAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.AlarmModel
import com.elfiltar.elfiltartechnician.databinding.ItemLayoutAlarmBinding

class AlarmAdapter(
    var itemsList: ArrayList<AlarmModel>,
    private val onItemClicked: (Int, AlarmModel) -> Unit,
) :
    BaseAdapter<AlarmAdapter.CustomViewHolder, AlarmModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutAlarmBinding.inflate(inflater, parent, false)
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