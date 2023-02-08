package com.elfiltar.elfiltartechnician.commons.customcomponent.expandableSelection

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.ashraf007.expandableselectionview.adapter.ExpandableItemAdapter
import com.ashraf007.expandableselectionview.extension.inflate
import com.ashraf007.expandableselectionview.view.ExpandableSelectionView
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import kotlinx.android.synthetic.main.custom_selection_header_layout.view.*

class CustomExpandableSelectionAdapter(
    private val items: List<BaseSelection>,
    var hint: String? = null,
    var startIcon: Int? = null
) : ExpandableItemAdapter {

    @DrawableRes
    var selectedStateResId: Int? = null

    @DrawableRes
    var collapsedStateResId: Int? = null

    @DrawableRes
    var expandedStateResId: Int? = null

    override fun inflateHeaderView(parent: ViewGroup): View {
        val view = parent.inflate(R.layout.custom_selection_header_layout)
        val headerTV = view.findViewById<TextView>(R.id.headerTV)
        headerTV.hint = hint
        if (startIcon!! != -1) {
            view.startIconIV.visibility = View.VISIBLE
            view.startIconIV.setImageResource(startIcon!!)
        } else {
            view.startIconIV.visibility = View.GONE
        }
        return view
    }

    override fun inflateItemView(parent: ViewGroup) =
        parent.inflate(R.layout.custom_selection_item_layout)

    override fun bindItemView(itemView: View, position: Int, selected: Boolean) {
        val itemTV = itemView.findViewById<TextView>(R.id.itemNameTV)
        itemTV.text = items[position].title!!
        itemView.findViewById<ImageView>(R.id.selectionIV)
            .setImageResource(selectedStateResId ?: R.drawable.icon_circle_check)
        itemView.findViewById<View>(R.id.selectionIV).isVisible = selected
    }

    override fun bindHeaderView(headerView: View, selectedIndices: List<Int>) {
        val headerTV = headerView.findViewById<TextView>(R.id.headerTV)
        if (selectedIndices.isEmpty()) {
            headerTV.text = null
        } else {
            headerTV.text = selectedIndices.joinToString { items[it].title!! }
        }
    }

    override fun getItemsCount() = if (items.size > 30) 30 else items.size

    override fun onViewStateChanged(headerView: View, state: ExpandableSelectionView.State) {
        val imageView = headerView.findViewById<ImageView>(R.id.listIndicatorIV)
        imageView.setImageResource(
            when (state) {
                ExpandableSelectionView.State.Expanded ->
                    expandedStateResId ?: R.drawable.icon_arrow_down
                ExpandableSelectionView.State.Collapsed ->
                    collapsedStateResId ?: R.drawable.icon_arrow_right
            }
        )
    }
}