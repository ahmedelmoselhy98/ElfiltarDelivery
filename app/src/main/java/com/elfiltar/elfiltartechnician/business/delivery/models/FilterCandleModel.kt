package com.elfiltar.elfiltartechnician.business.delivery.models

import com.elfiltar.elfiltartechnician.base.BaseModel

class FilterCandleModel:BaseModel() {

    var technician_id: Int? = null
    var water_quality_id: Int? = null
    var candle_number: Int? = null
    var change_after: Int? = null
    var created_at: String? = null
    var updated_at: String? = null

}