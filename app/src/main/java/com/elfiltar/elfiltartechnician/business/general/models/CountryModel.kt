package com.elfiltar.elfiltartechnician.business.general.models

import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryModel : BaseSelection() {
    @SerializedName("title_ar")
    @Expose
    var title_ar: String? = null

    @SerializedName("phone_code")
    @Expose
    var phone_code: String? = null
}