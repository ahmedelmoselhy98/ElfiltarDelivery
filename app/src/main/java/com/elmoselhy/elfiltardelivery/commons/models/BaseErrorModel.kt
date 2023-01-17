package com.elmoselhy.elfiltardelivery.commons.models

open class BaseErrorModel {
    var status: Int = 0
    var message: String = ""

    constructor(code: Int, message: String) {
        this.status = code
        this.message = message
    }
}