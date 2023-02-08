package com.elfiltar.elfiltartechnician.business.general.models

import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityModel:BaseSelection() {
    @SerializedName("city_name_ar")
    @Expose
    var city_name_ar: String? = null
}