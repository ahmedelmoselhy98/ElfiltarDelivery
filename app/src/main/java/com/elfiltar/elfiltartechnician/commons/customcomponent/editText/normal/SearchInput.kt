package com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import java.util.*


class SearchInput(context: Context, attrs: AttributeSet?) : BaseInput(context, attrs) {
    var timer = Timer()

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                timer.cancel()
//                timer = Timer()
//                timer.schedule(
//                    object : TimerTask() {
//                        override fun run() {
//                        }
//                    },
//                    1500
//                )
                typingCallback.onTyping(charSequence.toString())

            }
            override fun afterTextChanged(editable: Editable) {
            }
        })
    }
//    override fun setOnTextTyping(typingCallback: TypingCallback) {
//        super.setOnTextTyping(typingCallback)
//    }
}