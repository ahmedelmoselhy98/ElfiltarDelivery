package com.elfiltar.elfiltartechnician.business.authentication.activities.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elfiltar.elfiltartechnician.base.BaseAdapter
import com.elfiltar.elfiltartechnician.databinding.ItemLayoutIntroPageBinding

class IntroAdapterAdapter(
    var itemsList: ArrayList<Int>,
    private val onItemClicked: (Int, Int) -> Unit,
) :
    BaseAdapter<IntroAdapterAdapter.CustomViewHolder, Int>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutIntroPageBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutIntroPageBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    //onBindViewHolder
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        setUpData(holder, position)
        setUpActions(holder, position)
    }

    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.ivImage.setImageResource(itemsList[position])
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
    }

}