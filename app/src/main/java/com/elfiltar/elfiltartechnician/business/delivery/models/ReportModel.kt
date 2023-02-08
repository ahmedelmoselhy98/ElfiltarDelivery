package com.elfiltar.elfiltartechnician.business.delivery.models

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReportModel:BaseModel() {
    @SerializedName("activeClients")
    @Expose
    var activeClients: Int? = null
    @SerializedName("unActiveClients")
    @Expose
    var unActiveClients: Int? = null
    @SerializedName("activeMaintenance")
    @Expose
    var activeMaintenance: Int? = null
    @SerializedName("unActiveMaintenance")
    @Expose
    var unActiveMaintenance: Int? = null
    @SerializedName("earnings")
    @Expose
    var earnings: Int? = null
    @SerializedName("endRequests")
    @Expose
    var endRequests: Int? = null
    @SerializedName("currentRequests")
    @Expose
    var currentRequests: Int? = null
    @SerializedName("canceledRequests")
    @Expose
    var canceledRequests: Int? = null
}