package com.elmoselhy.elfiltardelivery.business.general.models

import com.elmoselhy.elfiltardelivery.base.BaseModel

class NotificationModel:BaseModel() {
    var message: String? = null

    var status: String? = null

    var address: String? = null

    var created_at: String? = null

    var user: User? = null

    class User : BaseModel() {

        var first_name: String? = null

        var last_name: String? = null

        var image: String? = null

        var phone_code: String? = null

        var phone: String? = null

        var address: String? = null

    }
}