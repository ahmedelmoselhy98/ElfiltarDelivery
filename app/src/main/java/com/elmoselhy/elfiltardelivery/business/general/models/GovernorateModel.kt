package com.elmoselhy.elfiltardelivery.business.general.models

import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GovernorateModel:BaseSelection() {
    @SerializedName("governorate_name_ar")
    @Expose
    var governorate_name_ar: String? = null
}