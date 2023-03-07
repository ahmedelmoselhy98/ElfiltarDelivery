package com.elfiltar.elfiltartechnician.commons.helpers

class MyConstants {
    object Public {
        var SERVER_BASE_URL = "https://elfiltar.com/"
        var SERVER_API_URL = "${SERVER_BASE_URL}api/"
        const val SPLASH_DISPLAY_LENGTH: Long = 2000
    }

    //Media types
    object MediaTypes {
        const val IMAGE = 0
        const val VIDEO = 1
        const val DOC = 2
        const val EXEL = 3
        const val PDF = 4
    }

    object Enums {
        //User Type
        object UserType {
            const val company = "company"
            const val technician = "technical"
        }

        //Order Status
        object OrderStatus {
            const val confirmed = "confirmed"
            const val new = "new"
            const val accept = "accept"
            const val on_way = "on_way"
            const val end = "end"
            const val cancel = "cancel"
            const val refuse = "refuse"
        }

        //Client Type
        object ClientType {
            const val client = "client"
            const val maintenance = "maintenance"
        }

        //Offer Type
        object OfferType {
            const val value = "val"
            const val percentage = "per"
        }

        //Pay types
        object PayType {
            const val cash = "cash"
            const val online = "online"
        }

        //Delivery Time types
        object DeliveryTimeType {
            const val now = "now"
            const val time = "time"
        }
    }


    object ApiMappingKey {
        const val user_id = "user_id"
        const val is_offer = "is_offer"
        const val id = "id"
        const val skip = "skip"
        const val take = "take"
        const val phone = "phone"
        const val email = "email"
        const val password = "password"
        const val username = "username"
        const val start_date = "start_date"
        const val end_date = "end_date"
        const val offer_value = "offer_value"
        const val offer_type = "offer_type"
        const val address = "address"
        const val lang = "lang"
        const val title = "title"
        const val lat = "lat"
        const val lng = "lng"
        const val latitude = "latitude"
        const val longitude = "longitude"
        const val name = "name"
        const val price = "price"
        const val pagination = "pagination"
        const val limit_per_page = "limit_per_page"
        const val target = "target"
        const val notifyId = "notifyId"
        const val category_id = "category_id"
        const val department_id = "department_id"
        const val city_id = "city_id"
        const val city = "city"
        const val neighborhood = "neighborhood"
        const val neighborhood_id = "neighborhood_id"
        const val delivery_date = "delivery_date"
        const val delivery_time = "delivery_time"
        const val delivery_time_type = "delivery_time_type"
        const val has_pay = "has_pay"
        const val order_amount = "order_amount"
        const val coupon_id = "coupon_id"
        const val car_id = "car_id"
        const val coupon_discount = "coupon_discount"
        const val pay_type = "pay_type"
        const val receiving_point_id = "receiving_point_id"
        const val first_receiving_point_id = "first_receiving_point_id"
        const val second_receiving_point_id = "second_receiving_point_id"
        const val delivery_point_id = "delivery_point_id"
        const val first_delivery_point_id = "first_delivery_point_id"
        const val second_delivery_point_id = "second_delivery_point_id"
        const val collection_point_id = "collection_point_id"
        const val order_type = "order_type"
        const val market_image = "market_image"
        const val tax_registration_number = "tax_registration_number"
        const val municipal_license = "municipal_license"
        const val intelligence_certificate = "intelligence_certificate"
        const val images = "images"
        const val text = "text"
        const val image = "image"
        const val delivery_payer = "delivery_payer"
        const val code = "code"
    }

    object Intent {
        const val ID = "id"
        const val CART_INPUT = "cart_input"
        const val TARGET = "Target"
        const val CATEGORY_NAME = "category_name"
        const val REGISTER = "register"
        const val CATEGORY_ID = "category_id"
        const val PROVIDER_ID = "provider_id"
        const val PRODUCT_ID = "product_id"
        const val ORDER_ID = "order_id"
        const val NOTIFY_ID = "notify_id"
        const val PRODUCT_DETAILS = "product_details"
        const val TITLE = "title"
        const val CHECKOUT = "checkout"
        const val TOTAL = "total"
        const val PRODUCT = "product"
        const val PICK_FROM_GALLERY = 10048
        const val EMAIL = "email"
        const val OTP_CODE = "otp_code"
        const val MEMBER_REQUEST = "memberRequest"
        const val MEDIA_FILES = "MediaFiles"
        const val MEMBER_RESPONSE = "memberResponse"
        const val FORGET_PASSWORD_RESPONSE = "forgetPasswordResponse"
        const val FILTER = "filter"
        const val FILTER_CODE = 1
        const val EXTERNAL_LOGIN = "ExternalLoginRequest"
        const val CHECKOUT_MESSAGE = "checkout_message"
    }


}