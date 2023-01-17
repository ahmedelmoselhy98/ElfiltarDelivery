package com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.material.helper

import android.content.Context
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.material.BaseMaterialEditText
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.material.CustomMaterialInputLayout

class CustomMaterialInputHelper {
    companion object {
        // set up typingCallback for inputs views
        fun setUpInputsTypingCallback(inputViews: ArrayList<CustomMaterialInputLayout>) {
            inputViews.forEach { inputView ->
                inputView!!.baseMaterialEditText.setOnTextTyping(
                    inputView,
                    object : BaseMaterialEditText.TypingCallback {
                        override fun onTyping(text: String) {
                        }
                    })
            }
        }

        fun checkIfInputsIsValid(
            context: Context,
            inputViews: ArrayList<CustomMaterialInputLayout>
        ): Boolean {
            var inputsViews = inputViews
            inputsViews.forEach { inputView ->
                if (!inputView!!.baseMaterialEditText.isValid) {
                    inputView!!.requestFocus()
                    inputView!!.error = inputView!!.baseMaterialEditText.error
                    return false
                }
            }
            return true
        }
    }
}
