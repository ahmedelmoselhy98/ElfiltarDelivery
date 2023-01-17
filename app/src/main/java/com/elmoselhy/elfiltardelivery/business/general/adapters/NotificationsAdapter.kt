package com.elmoselhy.elfiltardelivery.business.general.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.base.BaseAdapter
import com.elmoselhy.elfiltardelivery.business.general.models.NotificationModel
import com.elmoselhy.elfiltardelivery.databinding.ItemLayoutNotificationBinding

class NotificationsAdapter(
    var itemsList: ArrayList<NotificationModel>,
    private val onItemClicked: (Int, NotificationModel) -> Unit,
) :
    BaseAdapter<NotificationsAdapter.CustomViewHolder, NotificationModel>(itemsList) {
    //Custom View Holder
    open class CustomViewHolder(var binding: ItemLayoutNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    //onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding = ItemLayoutNotificationBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    //onBindViewHolder
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //start shimmer effect
        setUpData(holder, position)
        setUpActions(holder, position)
    }

    private fun setUpData(holder: CustomViewHolder, position: Int) {
        holder.binding.notificaiton = itemsList[position]
    }

    private fun setUpActions(holder: CustomViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
        }
    }

}