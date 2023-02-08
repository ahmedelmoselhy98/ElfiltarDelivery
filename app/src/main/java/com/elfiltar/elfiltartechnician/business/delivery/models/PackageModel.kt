package com.elfiltar.elfiltartechnician.business.delivery.models

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PackageModel : BaseModel() {
    @SerializedName("subscription_period")
    @Expose
    var subscription_period: Int? = null

    @SerializedName("price")
    @Expose
    var price: Double? = null

}