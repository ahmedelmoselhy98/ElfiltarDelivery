package com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.material

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.elmoselhy.elfiltardelivery.R


class PasswordMaterialEditText(context: Context, attrs: AttributeSet?) :
    BaseMaterialEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                typingCallback.onTyping(charSequence.toString())
                when {
                    isRequired && charSequence.toString().isNullOrEmpty() -> {
                        isValid = false
                        error = context.getString(R.string.error_message_required)
                        textInputLayout.error = error
                    }
                    charSequence.toString().length < 6 -> {
                        isValid = false
                        error = context.getString(R.string.error_message_password_length)
                        textInputLayout.error = error
                    }
                    else -> {
                        isValid = true
                        error = ""
                        textInputLayout.error = null
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}