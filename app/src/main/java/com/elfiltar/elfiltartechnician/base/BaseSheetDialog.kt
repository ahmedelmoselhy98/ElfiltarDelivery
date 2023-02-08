package com.elfiltar.elfiltartechnician.base

import android.content.Context
import android.view.View
import android.view.ViewParent
import com.elfiltar.elfiltartechnician.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

open class BaseSheetDialog(
    context: Context
) : BottomSheetDialog(context, R.style.BottomSheetDialog) {

    fun setUpSheetUi(viewParent: ViewParent) {
        val displayMetrics = context.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val mBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(viewParent as View)
        mBehavior.peekHeight = height
    }
}