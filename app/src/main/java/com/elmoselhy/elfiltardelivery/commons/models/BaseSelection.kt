package com.elmoselhy.elfiltardelivery.commons.models

import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseSelection : BaseModel() {
    var image: String? = null
}