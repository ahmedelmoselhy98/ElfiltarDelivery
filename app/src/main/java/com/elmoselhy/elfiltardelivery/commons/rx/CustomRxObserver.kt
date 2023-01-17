package com.elmoselhy.elfiltardelivery.commons.rx

import com.elmoselhy.elfiltardelivery.base.BaseViewModel
import com.elmoselhy.elfiltardelivery.commons.events.PhoneNotRegisteredEvent
import com.elmoselhy.elfiltardelivery.commons.models.BaseErrorModel
import com.elmoselhy.elfiltardelivery.commons.models.ServerErrorModel
import com.elmoselhy.elfiltardelivery.data.remote.apiservice.MyData
import io.reactivex.rxjava3.observers.DefaultObserver


abstract class CustomRxObserver<T>(var baseViewModel: BaseViewModel) : DefaultObserver<Any?>() {

    init {
        baseViewModel.isLoading.postValue(true)
    }


    override fun onNext(t: Any?) {
        baseViewModel.isLoading.postValue(false)
        var result = (t as MyData<T>)
        if (result.status == 200) {
            if (result.data != null)
                onResponse(result.data!!)
            else onResponse(t as T)
        } else if (result.status == 444) {
            RxBus.publish(PhoneNotRegisteredEvent())
        } else {
            baseViewModel.isLoading.postValue(false)
            var errorEvent = BaseErrorModel(result.status!!, result.msg!![0])
            var messageEvent = ServerErrorModel(
                errorEvent!!.status!!,
                errorEvent!!.message!!
            )
            RxBus.publish(messageEvent)
//            var errorMessage = ""
//            var jsonObject = result.msg
//            if (result.status === 400) {
//
////                val jsonArray = result.msg!!.getJSONArray("phone_code")
////                for (index in 0..jsonArray.length()) {
////                    errorMessage += Gson().fromJson(jsonArray.getString(index),String::class.java)
////                }
//                var errorEvent = BaseErrorModel(result.status!!, result.msg!!)
//                var messageEvent = ServerErrorModel(
//                    errorEvent!!.status!!,
//                    errorEvent!!.message!!
//                )
//                RxBus.publish(messageEvent)
//            }
//            if (result.status === 401) {
////                var errorEvent = BaseErrorModel(result.status!!, result.message!!)
////                var unAuthorizedEvent = UnAuthorizedErrorModel(
////                    errorEvent!!.status!!,
////                    errorEvent!!.message!!
////                )
////                RxBus.publish(unAuthorizedEvent)
//            } else {
//                errorMessage = result.msg.toString()
//                var errorEvent = BaseErrorModel(result.status!!, errorMessage)
//                var messageEvent = ServerErrorModel(
//                    errorEvent!!.status!!,
//                    errorEvent!!.message!!
//                )
//                RxBus.publish(messageEvent)
//            }
        }

//        baseViewModel.isLoading.postValue(false)

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        baseViewModel.isLoading.postValue(false)
        var errorEvent = BaseErrorModel(500, "Server error")
        var messageEvent = ServerErrorModel(
            errorEvent!!.status!!,
            errorEvent!!.message!!
        )
        RxBus.publish(messageEvent)
    }

    override fun onComplete() {}
    abstract fun onResponse(response: T)

    companion object {
        //Network errors
        const val UNAUTHORIZED = 401
        const val INTERNAL_SERVER_ERROR = 500
        const val INVALID_INPUT = 400
    }
}