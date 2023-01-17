package com.elmoselhy.elfiltardelivery.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseModel : Serializable {
    var id: Int? = null
    var title: String? = null
}