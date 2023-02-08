package com.elfiltar.elfiltartechnician.commons.customcomponent.editText.material

import android.content.Context
import android.util.AttributeSet
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import com.elfiltar.elfiltartechnician.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


open class BaseMaterialEditText(context: Context, attrs: AttributeSet?) :
    TextInputEditText(context, attrs) {

    var isValid = false
    var error = context.getString(R.string.error_message_required)
    var isRequired = true
    lateinit var typingCallback: TypingCallback
//    var typingCallback = object : TypingCallback {
//        override fun onTyping(text: String) {
//        }
//    }


    var textInputLayout = TextInputLayout(context, attrs)
    open fun setOnTextTyping(inputLayout: TextInputLayout, typingCallback: TypingCallback) {
        textInputLayout = inputLayout
        this.typingCallback = typingCallback
    }

    interface TypingCallback {
        fun onTyping(text: String)
    }

    open fun shakeError(): TranslateAnimation? {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7F)
        return shake
    }
}