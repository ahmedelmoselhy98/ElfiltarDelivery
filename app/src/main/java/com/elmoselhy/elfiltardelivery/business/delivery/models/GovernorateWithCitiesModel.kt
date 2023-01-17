package com.elmoselhy.elfiltardelivery.business.delivery.models

import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GovernorateWithCitiesModel:BaseModel() {
    @SerializedName("cities")
    @Expose
    var cities: ArrayList<BaseModel>? = null
}