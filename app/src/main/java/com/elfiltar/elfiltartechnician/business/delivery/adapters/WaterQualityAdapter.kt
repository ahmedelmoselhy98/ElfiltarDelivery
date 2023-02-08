package com.elfiltar.elfiltartechnician.business.delivery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elfiltar.elfiltartechnician.base.BaseAdapter
import com.elfiltar.elfiltartechnician.business.delivery.models.FilterCandleModel
import com.elfiltar.elfiltartechnician.business.delivery.models.WaterQualityModel
import com.elfiltar.elfiltartechnician.databinding.ItemLayoutWaterQualityBinding

class WaterQualityAdapter(
    var itemsList: ArrayList<WaterQualityModel>,
    private val onWaterQualityOptionsClicked: (View, Int, WaterQualityModel) -> Unit,
    private val onWaterCandleOptionsClicked: (View, Int, FilterCandleModel) -> Unit,
    private val onItemClicked: (Int, WaterQualityModel) -> Unit,
) :
    BaseAdapter<WaterQualityAdapter.CustomViewHolder, WaterQualityModel>(itemsList) {

    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutWaterQualityBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutWaterQualityBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    //onBindViewHolder
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        setUpData(holder, position)
        setUpActions(holder, position)
    }

    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.title = itemsList[position].title
        holder.binding.recyclerView.adapter =
            FilterCandleAdapter(
                itemsList[position].data,
                onWaterCandleOptionsClicked = { view, position, model ->
                    onWaterCandleOptionsClicked(view, position, model)
                },
                onItemClicked = { position, model ->
                })
        holder.binding.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
        holder.binding.ivMore.setOnClickListener {
            onWaterQualityOptionsClicked(it, position, itemsList[position])
        }
    }

}