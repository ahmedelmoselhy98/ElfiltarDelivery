package com.elfiltar.elfiltartechnician.commons.customcomponent.dropdownexpandablerecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elfiltar.elfiltartechnician.base.BaseAdapter
import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.databinding.ItemLayoutSelectionBinding

class ItemSelectionAdapter(
    var itemsList: List<BaseModel>,
    private val onItemSelected: (Int, BaseModel) -> Unit,
    private val onItemUnSelected: (Int, BaseModel) -> Unit
) : BaseAdapter<ItemSelectionAdapter.CustomViewHolder, BaseModel>(itemsList) {
    open class CustomViewHolder(var binding: ItemLayoutSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutSelectionBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //start shimmer effect
        //startShimmerEffect(holder.binding.contentLayout, holder.binding.shimmerLayout)
        //set up item actions
        setUpData(holder, position)
        setUpActions(holder, position)
    }

    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.name = itemsList[position].title
        if (currentSelectedItemPosition == position) {
            holder.binding.ivCheck.visibility = View.VISIBLE
        } else {
            holder.binding.ivCheck.visibility = View.GONE
        }
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if (currentSelectedItemPosition == position) {
                unSelectItem()
                onItemUnSelected(position, itemsList[position])
            } else {
                selectItem(position)
                onItemSelected(position, itemsList[position])
            }
        }
    }


}