package com.elmoselhy.elfiltardelivery.business.delivery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.FilterCandleModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.ItemLayoutFilterCandleBinding

class FilterCandleAdapter(
    var itemsList: ArrayList<FilterCandleModel>,
    private val onWaterCandleOptionsClicked: (View, Int, FilterCandleModel) -> Unit,
    private val onItemClicked: (Int, FilterCandleModel) -> Unit,
) :
    BaseAdapter<FilterCandleAdapter.CustomViewHolder, FilterCandleModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutFilterCandleBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutFilterCandleBinding.inflate(inflater, parent, false)
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
//        holder.binding.etChangeAfter.setOnTextTyping(object :BaseInput.TypingCallback{
//            override fun onTyping(text: String) {
//            }
//        })
        holder.binding.ivMore.setOnClickListener {
            if (holder.binding.etChangeAfter.text.toString().isNullOrEmpty()) {
                holder.binding.etChangeAfter.error =
                    holder.itemView.context.getString(R.string.error_message_required)
                return@setOnClickListener
            }
            holder.binding.etChangeAfter.error = null
            itemsList[position].change_after = holder.binding.etChangeAfter.text.toString().toInt()
            onWaterCandleOptionsClicked(it, position, itemsList[position])
        }
    }
}