package com.elmoselhy.elfiltardelivery.business.delivery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.PackageModel
import com.elmoselhy.elfiltardelivery.databinding.ItemLayoutPackageBinding

class PackageAdapter(
    var itemsList: ArrayList<PackageModel>,
    private val onItemClicked: (Int, PackageModel) -> Unit,
) :
    BaseAdapter<PackageAdapter.CustomViewHolder, PackageModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutPackageBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        currentSelectedItemPosition = -1
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutPackageBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    //onBindViewHolder
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        setUpData(holder, position)
        setUpActions(holder, position)
    }
    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.item = itemsList[position]
        if (currentSelectedItemPosition == position) {
            holder.binding.ivSelector.visibility = View.VISIBLE
            holder.binding.sectionContainer.setBackgroundResource(R.drawable.background_stroke_rectangle_primary)
            holder.binding.sectionPrice.setBackgroundResource(R.drawable.background_solid_rectangle_primary_10a)
        } else {
            holder.binding.ivSelector.visibility = View.GONE
            holder.binding.sectionContainer.setBackgroundResource(R.drawable.background_stroke_rectangle_grey)
            holder.binding.sectionPrice.setBackgroundResource(R.drawable.background_solid_rectangle_grey_10a)
        }
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            selectItem(position)
            onItemClicked(position, itemsList[position])
        }
    }

}