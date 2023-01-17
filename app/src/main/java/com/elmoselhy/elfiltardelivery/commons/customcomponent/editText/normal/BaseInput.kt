package com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal

import android.content.Context
import android.util.AttributeSet
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatEditText


open class BaseInput(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    var isValid = false
    var isRequired = true
    var typingCallback = object : TypingCallback {
        override fun onTyping(text: String) {

        }
    }

    open fun setOnTextTyping(typingCallback: TypingCallback) {
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