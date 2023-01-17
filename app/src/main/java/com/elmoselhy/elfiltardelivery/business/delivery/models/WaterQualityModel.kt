package com.elmoselhy.elfiltardelivery.business.delivery.models

import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WaterQualityModel : BaseModel() {
    var title_ar: String? = null
    var title_en: String? = null
    var data: ArrayList<FilterCandleModel> = ArrayList()
}