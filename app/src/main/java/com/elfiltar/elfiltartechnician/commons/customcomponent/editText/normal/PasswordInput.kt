package com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.elfiltar.elfiltartechnician.R


class PasswordInput(context: Context, attrs: AttributeSet?) : BaseInput(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                typingCallback.onTyping(charSequence.toString())
                if (isRequired)
                    when {
                        charSequence.toString().isNullOrEmpty() -> {
                            isValid = false
                            error = context.getString(R.string.error_message_required)
                        }
                        charSequence.toString().length < 6 -> {
                            isValid = false
                            error = context.getString(R.string.error_message_password_length)
                        }
                        else -> {
                            isValid = true
                            error = null
                        }
                    }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    fun isEmailValid(input: String): Boolean {
        return !input.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    override fun setOnTextTyping(typingCallback: TypingCallback) {
        super.setOnTextTyping(typingCallback)
    }


}