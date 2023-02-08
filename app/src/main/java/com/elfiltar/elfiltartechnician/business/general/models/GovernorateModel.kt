package com.elfiltar.elfiltartechnician.business.general.models

import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GovernorateModel:BaseSelection() {
    @SerializedName("governorate_name_ar")
    @Expose
    var governorate_name_ar: String? = null
}