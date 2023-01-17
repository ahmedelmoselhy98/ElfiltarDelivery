package com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper

import android.content.Context
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput

class ElmoselhyInputHelper {
    companion object {
        // set up typingCallback for inputs views
        fun setUpInputsTypingCallback(inputViews: ArrayList<BaseInput>) {
            inputViews.forEach { inputView ->
                inputView!!.setOnTextTyping(
                    object : BaseInput.TypingCallback {
                        override fun onTyping(text: String) {
                        }
                    })
            }
        }

        fun checkIfInputsIsValid(
            context: Context,
            inputViews: ArrayList<BaseInput>
        ): Boolean {
            var inputsViews = inputViews
            inputsViews.forEach { inputView ->
                if (!inputView!!.isValid) {
                    inputView!!.requestFocus()
                    inputView!!.error = context.getString(R.string.error_message_required)
                    return false
                }
            }
            return true
        }
    }
}
