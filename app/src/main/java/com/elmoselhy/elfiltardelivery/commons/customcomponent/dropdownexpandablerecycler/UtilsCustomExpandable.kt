package com.elmoselhy.elfiltardelivery.commons.customcomponent.dropdownexpandablerecycler

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.material.BaseMaterialEditText
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.material.CustomMaterialInputLayout
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.TextInput
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import net.cachapa.expandablelayout.ExpandableLayout

class UtilsCustomExpandable {
    companion object {
        fun setUpExpandList(
            context: Context,
            recyclerView: RecyclerView,
            itemsList: List<BaseModel>,
            expandableLayout: ExpandableLayout,
            title: String,
            tvTitle: TextView,
            tvEmpty: TextView,
            textInput: TextInput,
            startIcon: Int,
            onItemSelected: (Int, BaseModel) -> Unit,
            onItemUnSelected: (Int, BaseModel) -> Unit
        ) {
            tvTitle.text = title
            var tempList = ArrayList<BaseModel>()
            tempList.addAll(itemsList)
            if (tempList.isNullOrEmpty()) {
                tvEmpty.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                tvEmpty.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
            MyUtils.setTextViewDrawable(tvTitle, startIcon, R.drawable.icon_arrow_bottom)

            var adapter = ItemSelectionAdapter(tempList, onItemSelected = { position, item ->
                tvTitle.text = item.title
                onItemSelected(position, item)
                expandableLayout.collapse()
            }, onItemUnSelected = { position, item ->
                tvTitle.text = title
                onItemUnSelected(position, item)
                expandableLayout.collapse()
            })
            adapter.currentSelectedItemPosition = -1
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)

            expandableLayout.setOnExpansionUpdateListener { expansionFraction, state ->
                when (state) {
                    ExpandableLayout.State.EXPANDED -> {
                        MyUtils.setTextViewDrawable(tvTitle, startIcon, R.drawable.icon_arrow_top)
                    }
                    ExpandableLayout.State.COLLAPSED -> {
                        MyUtils.setTextViewDrawable(
                            tvTitle,
                            startIcon,
                            R.drawable.icon_arrow_bottom
                        )
                    }
                }
            }
            tvTitle.setOnClickListener {
                if (expandableLayout.isExpanded)
                    expandableLayout.collapse()
                else {
                    if (!itemsList.isNullOrEmpty()) {
                        expandableLayout.expand()
                    }
                }
            }

            textInput.isRequired = false
            textInput.setOnTextTyping(object : BaseInput.TypingCallback {
                override fun onTyping(text: String) {
                    tempList.clear()
                    if (!text.isNullOrEmpty())
                        itemsList.forEachIndexed { index, baseSelection ->
                            if (baseSelection.title!!.toLowerCase()
                                    .contains(text.toLowerCase())
                            ) tempList.add(baseSelection)
                        }
                    else {
                        tempList.clear()
                        tempList.addAll(itemsList)
                    }
                    if (tempList.isNullOrEmpty()) {
                        tvEmpty.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        tvEmpty.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                    adapter.replaceDataList(tempList)
                }
            })
        }
    }


    // helpers
    /**
    implementation 'com.github.cachapa:ExpandableLayout:2.9.2'
     */

    /**
    ///icon_arrow_top////////////////////////////////
    <vector xmlns:android="http://schemas.android.com/apk/res/android" xmlns:aapt="http://schemas.android.com/aapt"
    android:viewportWidth="16"
    android:viewportHeight="9"
    android:width="16dp"
    android:height="9dp">
    <path
    android:pathData="M15.687 8.753a0.89 0.89 0 0 0 0.051 -1.308L8.718 0.3a1.019 1.019 0 0 0 -1.436 0L0.262 7.444a0.89 0.89 0 0 0 0.051 1.309A1.02 1.02 0 0 0 1.7 8.7L8 2.287 14.3 8.7a1.02 1.02 0 0 0 1.384 0.049z"
    android:fillColor="#000000" />
    </vector>
     *
     *
    ///icon_arrow_bottom//////////////////////////////
    <vector xmlns:android="http://schemas.android.com/apk/res/android" xmlns:aapt="http://schemas.android.com/aapt"
    android:viewportWidth="16"
    android:viewportHeight="9"
    android:width="16dp"
    android:height="9dp">
    <path
    android:pathData="M0.313 0.247a0.89 0.89 0 0 0 -0.051 1.308L7.282 8.7a1.019 1.019 0 0 0 1.436 0l7.02 -7.144a0.89 0.89 0 0 0 -0.051 -1.309A1.02 1.02 0 0 0 14.3 0.3L8 6.713 1.7 0.3A1.02 1.02 0 0 0 0.316 0.251z"
    android:fillColor="#000000" />
    </vector>

    ///item_layout_selection//////////////////////////
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
    <variable
    name="name"
    type="String" />
    </data>

    <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="@dimen/_10sdp"
    android:orientation="vertical">

    <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="@dimen/_10sdp"
    android:gravity="center"
    android:orientation="horizontal">

    <ImageView
    android:id="@+id/iv_check"
    android:layout_width="@dimen/_25sdp"
    android:layout_height="@dimen/_25sdp"
    android:visibility="invisible"
    android:src="@drawable/icon_box_check_fill" />

    <TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/_8sdp"
    android:text="Lorem Ipsum"
    android:textColor="@color/text_color_black"
    android:textSize="@dimen/_15ssp" />
    </LinearLayout>

    <View style="@style/LineStyle"
    android:id="@+id/v_line"/>
    </LinearLayout>
    </layout>











     */


}