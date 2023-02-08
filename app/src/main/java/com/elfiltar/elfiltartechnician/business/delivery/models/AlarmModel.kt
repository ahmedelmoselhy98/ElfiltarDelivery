package com.elfiltar.elfiltartechnician.business.delivery.models

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AlarmModel:BaseModel() {
    @SerializedName("technician_id")
    @Expose
    var technician_id: Int? = null
    @SerializedName("technician_client_id")
    @Expose
    var technician_client_id: Int? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("notes")
    @Expose
    var notes: String? = null
    @SerializedName("updated_at")
    @Expose
    var updated_at: String? = null
}