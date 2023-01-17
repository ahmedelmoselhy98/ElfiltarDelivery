package com.elmoselhy.elfiltardelivery.business.general.models

import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityModel:BaseSelection() {
    @SerializedName("city_name_ar")
    @Expose
    var city_name_ar: String? = null
}