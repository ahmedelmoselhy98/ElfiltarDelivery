package com.elmoselhy.elfiltardelivery.commons.helpers

class MyEnums {
    enum class OrderStatus(val value: Int) {
        Requested(1),
        Accepted(2),
        OnTheWay(3),
        Delivered(4),
        Cancelled(100),
        Rejected(200),
        Returned(300),
    }
}