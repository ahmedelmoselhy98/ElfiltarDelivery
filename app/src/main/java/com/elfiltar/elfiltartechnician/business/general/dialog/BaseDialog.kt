package com.elfiltar.elfiltartechnician.business.general.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager

open class BaseDialog(
    context: Context,
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    init {
        setUpDialogUi()
    }
    fun setUpDialogUi() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }
}