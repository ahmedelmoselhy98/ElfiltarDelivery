package com.elmoselhy.elfiltardelivery.business.authentication.models

import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.commons.models.BaseSelection
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileModel : BaseModel() {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("phone_code")
    @Expose
    var phone_code: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("tax_number")
    @Expose
    var tax_number: String? = null

    @SerializedName("tax_number_image")
    @Expose
    var tax_number_image: String? = null

    @SerializedName("commercial_number")
    @Expose
    var commercial_number: String? = null

    @SerializedName("commercial_number_image")
    @Expose
    var commercial_number_image: String? = null

    @SerializedName("nationality_id_image")
    @Expose
    var nationality_id_image: String? = null

    @SerializedName("country_id")
    @Expose
    var country_id: Int? = null

    @SerializedName("package_sub_end_days")
    @Expose
    var package_sub_end_days: Int? = null

    @SerializedName("end_orders")
    @Expose
    var end_orders: Int? = null

    @SerializedName("candles_count")
    @Expose
    var candles_count: Int? = null

    @SerializedName("governorate_id")
    @Expose
    var governorate_id: Int? = null

    @SerializedName("is_active")
    @Expose
    var is_active: Int? = null

    @SerializedName("country")
    @Expose
    var country: BaseModel? = null

    @SerializedName("governorate")
    @Expose
    var governorate: BaseModel? = null

    @SerializedName("cities")
    @Expose
    var cities: ArrayList<BaseModel>? = null

    @SerializedName("subscription_end")
    @Expose
    var subscription_end: String? = null

}