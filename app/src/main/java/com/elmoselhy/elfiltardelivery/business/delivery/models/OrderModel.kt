package com.elmoselhy.elfiltardelivery.business.delivery.models

import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class OrderModel : BaseModel() {
    var text: String? = null
    var comment: String? = null

    var status: String? = null
    var price: Double? = null

    var created_at: String? = null

    var reason_for_cancellation: String? = null

    var user: User? = null

    class User : BaseModel() {

        var first_name: String? = null

        var last_name: String? = null
        var governorate: BaseModel? = null
        var country: BaseModel? = null

        var image: String? = null

        var phone_code: String? = null

        var phone: String? = null

        var address: String? = null

    }
}