package com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.elmoselhy.elfiltardelivery.R


class PhoneInput(context: Context, attrs: AttributeSet?) : BaseInput(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                typingCallback.onTyping(charSequence.toString())
                if (isRequired)
                    testValidation(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {
            }
        })
    }
    fun testValidation(text: String){
        if (!text.isNullOrEmpty()) {
            isValid = true
            error = null
        } else{
            isValid = false
            error = context.getString(R.string.error_message_required)
        }
    }

}