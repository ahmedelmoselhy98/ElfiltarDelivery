package com.elmoselhy.elfiltardelivery.data.remote.apiservice

import com.elmoselhy.elfiltardelivery.commons.helpers.MyConstants
import com.elmoselhy.elfiltardelivery.data.local.session.SessionHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HeaderInterceptor : Interceptor {
    @Inject
    constructor()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return addRequestHeaders(chain)
    }

    @Inject
    lateinit var sessionHelper: SessionHelper
    private fun addRequestHeaders(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val originalHttpUrl = origin.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(MyConstants.ApiMappingKey.lang, sessionHelper.getUserLanguageCode())
            .build()
        var request: Request? = null
        var builder: Request.Builder? = null
        builder = chain.request().newBuilder()
            .url(url)
//            .addHeader("x-access-token", sessionHelper.userToken()!!)
//            .addHeader("AcceptLanguage", sessionHelper.userLanguageCode)
//            .addHeader("RegistrationToken", sessionHelper.pushNotificationToken)
//            .addHeader("OSVersion", Build.VERSION.RELEASE.toString())
//            .addHeader("ConnectionType", connectivity.networkConnectionType)
//            .addHeader("AppVersion", BuildConfig.VERSION_NAME)
//            .addHeader("MachineType", Build.BRAND + " " + Build.MODEL)
//            .addHeader("OSType", ANDROID)
//            .addHeader("MachineId", sessionHelper.deviceId)
//            .addHeader("Latitude", "0")
//            .addHeader("Longitude", "0")
            .addHeader("Accept", "application/json")
        try {
            if (sessionHelper.getPushNotificationToken() != null) {
                builder.addHeader("firebase_token", sessionHelper.getPushNotificationToken()!!)
            }
            if (sessionHelper.getUserSession() != null) {
                builder.addHeader("technician_id", ""+sessionHelper.getUserSession()!!.id)
            }
            request = builder.build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return chain.proceed(request!!)
    }


    private fun internalServerError(response: Response): Boolean {
        //500 internal server error
        return (response.code == 500)
    }

    private fun shouldLogout(response: Response): Boolean {
        // 401 and auth token means that we need to logout
        return (response.code == 401)
    }
}