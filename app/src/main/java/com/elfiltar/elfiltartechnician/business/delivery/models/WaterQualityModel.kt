package com.elfiltar.elfiltartechnician.business.delivery.models

import com.elfiltar.elfiltartechnician.base.BaseModel

class WaterQualityModel : BaseModel() {
    var title_ar: String? = null
    var title_en: String? = null
    var data: ArrayList<FilterCandleModel> = ArrayList()
}