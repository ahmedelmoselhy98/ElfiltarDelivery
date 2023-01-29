package com.elmoselhy.elfiltardelivery.business.delivery.models

import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ClientModel : BaseModel() {
    @SerializedName("first_name")
    @Expose
    var first_name: String? = null

    @SerializedName("last_name")
    @Expose
    var last_name: String? = null

    @SerializedName("created_at")
    @Expose
    var created_at: String? = null

    @SerializedName("phone_code")
    @Expose
    var phone_code: String? = null

    @SerializedName("next_text_notification")
    @Expose
    var next_text_notification: String? = null

    @SerializedName("next_date_notification")
    @Expose
    var next_date_notification: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("facebook_link")
    @Expose
    var facebook_link: String? = null

    @SerializedName("city_id")
    @Expose
    var city_id: Int? = null

    @SerializedName("city")
    @Expose
    var city: BaseModel? = null

    @SerializedName("governorate")
    @Expose
    var governorate: BaseModel? = null

    @SerializedName("technician_id")
    @Expose
    var technician_id: Int? = null

    @SerializedName("stages_number")
    @Expose
    var stages_number: Int? = null

    @SerializedName("water_quality")
    @Expose
    var water_quality: WaterQualityModel? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("governorate_id")
    @Expose
    var governorate_id: Int? = null

    @SerializedName("date_of_contract")
    @Expose
    var date_of_contract: String? = null

    @SerializedName("notes")
    @Expose
    var notes: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

}