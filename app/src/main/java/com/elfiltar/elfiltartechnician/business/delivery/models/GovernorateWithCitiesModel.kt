package com.elfiltar.elfiltartechnician.business.delivery.models

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GovernorateWithCitiesModel:BaseModel() {
    @SerializedName("cities")
    @Expose
    var cities: ArrayList<BaseModel>? = null
}