package com.elfiltar.elfiltartechnician.commons.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ErrorResultModel {
    @SerializedName("message")
    @Expose
    var message: String = ""

    @SerializedName("code")
    @Expose
    var status: Int = 0

    constructor(message: String) {
        this.message = message
    }
}