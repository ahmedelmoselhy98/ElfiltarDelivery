package com.elfiltar.elfiltartechnician.commons.customcomponent.expandableSelection

import android.content.Context
import android.util.AttributeSet
import com.ashraf007.expandableselectionview.view.ExpandableSelectionView
import com.elfiltar.elfiltartechnician.commons.models.BaseSelection

class CustomExpandableSelection @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CustomExpandableSelectionView(context, attrs, defStyleAttr) {

    var isValid: Boolean = false
    var autoCollapseOnSelection: Boolean = true
    var selectionListener: ((Int?) -> Unit)? = null
    var unSelectionListener: ((Int?) -> Unit)? = null
    val selectedIndex: Int?
        get() = getSelectedIndices().firstOrNull()
    var dataList = ArrayList<BaseSelection>()
    var title = ""
    var startIcon = -1


    fun setUpSelectionView(
        list: List<BaseSelection>,
        title: String,
        startIcon: Int,
        selectionListener: ((Int?) -> Unit),
        unSelectionListener: ((Int?) -> Unit)
    ) {
        this.title = title
        this.startIcon = startIcon
        dataList.clear()
        dataList.addAll(list)
        val expandableAdapter =
            CustomExpandableSelectionAdapter(list, title, startIcon)
        setAdapter(expandableAdapter)
        this.selectionListener = selectionListener
        this.unSelectionListener = unSelectionListener

        if (!dataList.isNullOrEmpty()) {
            setSelectedItemById(dataList[0])
        }
    }

    fun setSelectedItemById(model: BaseSelection) {
        if (dataList.isNullOrEmpty())
            return
        dataList.forEachIndexed { index, baseSelection ->
            if (model.id == baseSelection.id) {
                handleItemClick(index)
            }
        }
    }

    fun setSelectedItemById(id: Int) {
        if (dataList.isNullOrEmpty())
            return
        dataList.forEachIndexed { index, baseSelection ->
            if (id == baseSelection.id) {
                handleItemClick(index)
            }
        }
    }

    fun clearData() {
        dataList.clear()
        val expandableAdapter =
            CustomExpandableSelectionAdapter(dataList, title, startIcon)
        setAdapter(expandableAdapter)
    }

    override fun handleItemClick(index: Int) {
        val selectedItems = getSelectedIndices()
        if (isSelected(index)) {
            unSelectItem(index)
            notifyUnSelectionListener(index)
            isValid = false
            setError(null)
        } else {
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            notifySelectionListener(index)
            isValid = true
            setError(null)
        }
        if (autoCollapseOnSelection)
            setState(ExpandableSelectionView.State.Collapsed)
    }

    fun selectIndex(index: Int, notifyListener: Boolean = true) {
        if (!isSelected(index)) {
            val selectedItems = getSelectedIndices()
            if (selectedItems.isNotEmpty()) {
                unSelectItem(selectedItems.first())
            }
            selectItem(index)
            if (notifyListener) {
                notifySelectionListener(index)
            }
        }
    }

    override fun clearSelection() {
        super.clearSelection()
        notifySelectionListener(null)
    }

    private fun notifySelectionListener(index: Int?) {
        selectionListener?.invoke(index)
    }

    private fun notifyUnSelectionListener(index: Int?) {
        unSelectionListener?.invoke(index)
    }
}