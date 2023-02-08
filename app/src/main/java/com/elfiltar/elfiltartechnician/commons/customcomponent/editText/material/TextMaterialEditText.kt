package com.elfiltar.elfiltartechnician.commons.customcomponent.editText.material

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.elfiltar.elfiltartechnician.R

class TextMaterialEditText(context: Context, attrs: AttributeSet?) :
    BaseMaterialEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                typingCallback.onTyping(charSequence.toString())

                when {
                    isRequired && charSequence.toString().isNullOrEmpty() -> {
                        isValid = false
                        error = context.getString(R.string.error_message_required)
                        textInputLayout.error = error
                    }
                    else -> {
                        isValid = true
                        error = ""
                        textInputLayout.error = null
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

}