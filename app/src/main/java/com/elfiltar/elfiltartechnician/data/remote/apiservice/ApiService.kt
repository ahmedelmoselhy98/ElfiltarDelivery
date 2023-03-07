package com.elfiltar.elfiltartechnician.data.remote.apiservice

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.business.authentication.models.ProfileModel
import com.elfiltar.elfiltartechnician.business.delivery.models.*
import com.elfiltar.elfiltartechnician.business.general.models.NotificationModel
import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    /*************************** Authentication *******************************/
    @GET("getTechnicianProfile")
    fun getTechnicianProfile(@Query("technician_id") technicianId: Int): Observable<MyData<ProfileModel>>

    @POST("technicalLogin")
    fun login(@Body req: Any?): Observable<MyData<ProfileModel>>

    @Multipart
    @POST("technical_register")
    fun registerAsTechnician(
        @PartMap map: HashMap<String, @JvmSuppressWildcards RequestBody>,
        @Part("cities[]") cities: ArrayList<Int>,
        @Part("governorates[]") governorates: ArrayList<Int>,
        @Part image: MultipartBody.Part,
        @Part nationality_id_image: MultipartBody.Part
    ): Observable<MyData<ProfileModel>>

    @Multipart
    @POST("updateTechnicalProfile")
    fun updateProfile(
        @PartMap map: HashMap<String, @JvmSuppressWildcards RequestBody>,
        @Part("cities[]") cities: ArrayList<Int>,
        @Part("governorates[]") governorates: ArrayList<Int>,
        @Part image: MultipartBody.Part? = null,
        @Part nationality_id_image: MultipartBody.Part? = null,
        @Part tax_number_image: MultipartBody.Part? = null,
        @Part commercial_number_image: MultipartBody.Part? = null
    ): Observable<MyData<ProfileModel>>

    @Multipart
    @POST("technical_register")
    fun registerAsCompany(
        @PartMap map: HashMap<String, @JvmSuppressWildcards RequestBody>,
        @Part("cities[]") cities: ArrayList<Int>,
        @Part("governorates[]") governorates: ArrayList<Int>,
        @Part image: MultipartBody.Part,
        @Part tax_number_image: MultipartBody.Part,
        @Part commercial_number_image: MultipartBody.Part
    ): Observable<MyData<ProfileModel>>

    @POST("contactUs")
    fun contactUs(
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<Any>>


    /*************************** General *******************************/
    @GET("setting/governorateWithCities")
    fun getGovernorateWithCities(): Observable<MyData<ArrayList<GovernorateWithCitiesModel>>>

    @GET("countries")
    fun getCountries(): Observable<MyData<ArrayList<BaseSelection>>>

    @GET("governorates")
    fun getGovernorates(@Query("country_id") country_id: Int): Observable<MyData<ArrayList<BaseSelection>>>

    @GET("citiesOfGovernorate")
    fun getCities(@Query("governorate_id") country_id: Int): Observable<MyData<ArrayList<BaseSelection>>>


    /*************************** Packages *******************************/
    @GET("packages")
    fun getPackages(): Observable<MyData<ArrayList<PackageModel>>>

    @POST("subscriptions")
    fun subscribePackage(
        @Query("technician_id") technicianId: Int,
        @Query("package_id") packageId: Int
    ): Observable<MyData<String>>


    /*************************** Technician Alarm *******************************/

    @GET("technicianNotifications")
    fun getTechnicianAlarms(
        @Query("technician_id") technicianId: Int,
        @Query("technician_client_id") technicianClientId: Int
    ): Observable<MyData<ArrayList<AlarmModel>>>

    @POST("technicianNotifications")
    fun addTechnicianNotifications(
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<AlarmModel>>


    /*************************** Notifications *******************************/
    @GET("notifications")
    fun getNotifications(
        @Query("technician_id") technicianId: Int
    ): Observable<MyData<ArrayList<NotificationModel>>>

    @GET("unReadNotifications")
    fun unReadNotifications(
        @Query("technician_id") technicianId: Int
    ): Observable<MyData<Int>>


    @POST("deleteAllNotifications")
    fun deleteAllNotifications(
        @Query("technician_id") technicianId: Int
    ): Observable<MyData<Any>>


    /*************************** Filters *******************************/

    @GET("technicianFilterSetting")
    fun getFilterSettings(
        @Query("technician_id") technicianId: Int
    ): Observable<MyData<ArrayList<WaterQualityModel>>>

    @POST("technicianFilterSetting")
    fun createCandle(
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<Any>>

    @PUT("technicianFilterSetting/{id}")
    fun updateCandle(
        @Path("id") id: Int,
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<Any>>


    @GET("waterQualities")
    fun getWaterQualities(
        @Query("technician_id") technicianId: Int
    ): Observable<MyData<ArrayList<BaseModel>>>


    @GET("technicianReport")
    fun technicianReport(
        @Query("technician_id") technicianId: Int
    ): Observable<MyData<ReportModel>>

    @POST("waterQualities")
    fun createWaterQuality(
        @Body req: Any?
    ): Observable<MyData<Any>>

    @PUT("waterQualities/{id}")
    fun updateWaterQuality(
        @Path("id") id: Int,
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<Any>>


    @DELETE("waterQualities/{id}")
    fun deleteWaterQuality(
        @Path("id") id: Int
    ): Observable<MyData<Any>>


    /*************************** Clients *******************************/
    @GET("technicianClients")
    fun getAllClients(
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<ArrayList<ClientModel>>>

    @GET("technicianClientDetails")
    fun getClientDetails(
        @Query("client_id")id: String
    ): Observable<MyData<ClientModel>>

    @GET("searchInTechnicianClients")
    fun getClients(
        @QueryMap() map: HashMap<String, @JvmSuppressWildcards Any>
    ): Observable<MyData<ArrayList<ClientModel>>>

    @POST("technicianClients")
    fun addClient(
        @Body req: Any?
    ): Observable<MyData<ClientModel>>

    @POST("updateClientOfTechnician")
    fun updateClient(
        @Body req: Any?
    ): Observable<MyData<ClientModel>>

    /*************************** Order *******************************/
    @GET("maintenanceRequestForTechnician")
    fun getOrders(
        @Query("technician_id") technicianId: Int,
        @Query("type") type: String
    ): Observable<MyData<ArrayList<OrderModel>>>

    @GET("maintenanceRequestDetails")
    fun getOrderDetails(@Query("maintenance_request_id") orderId: Int): Observable<MyData<OrderModel>>

    @POST("changeMaintenanceRequestStatus")
    fun changeOrderStatus(
        @Query("maintenance_request_id") orderId: Int,
        @Query("reason_for_cancellation") text: String,
        @Query("status") status: String
    ): Observable<MyData<OrderDetailsModel>>

    @POST("changeMaintenanceRequestStatus")
    fun changeOrderStatus(
        @Query("maintenance_request_id") orderId: Int,
        @Query("status") status: String
    ): Observable<MyData<OrderDetailsModel>>

    @POST("changeMaintenanceRequestStatus")
    fun finishOrder(
        @Query("maintenance_request_id") orderId: Int,
        @Query("status") status: String,
        @Query("price") price: Double
    ): Observable<MyData<OrderDetailsModel>>


}