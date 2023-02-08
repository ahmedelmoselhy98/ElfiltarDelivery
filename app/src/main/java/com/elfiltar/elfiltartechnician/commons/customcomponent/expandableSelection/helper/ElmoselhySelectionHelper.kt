package com.elfiltar.elfiltartechnician.commons.customcomponent.expandableSelection.helper

import android.content.Context
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.commons.customcomponent.expandableSelection.CustomExpandableSelection

class ElmoselhySelectionHelper {
    companion object {
        fun checkIfSelectionIsValid(
            context: Context,
            inputViews: ArrayList<CustomExpandableSelection>
        ): Boolean {
            var inputsViews = inputViews
            inputsViews.forEach { inputView ->
                if (!inputView!!.isValid) {
                    inputView!!.requestFocus()
                    inputView!!.setError(context.getString(R.string.error_message_required))
                    return false
                }
            }
            return true
        }
    }
}
