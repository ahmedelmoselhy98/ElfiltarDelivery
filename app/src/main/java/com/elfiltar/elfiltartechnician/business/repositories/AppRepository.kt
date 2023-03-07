package com.elfiltar.elfiltartechnician.business.repositories

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.business.authentication.models.ProfileModel
import com.elfiltar.elfiltartechnician.business.delivery.models.*
import com.elfiltar.elfiltartechnician.business.general.models.NotificationModel
import com.elfiltar.elfiltartechnician.commons.helpers.MyConstants
import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import com.elfiltar.elfiltartechnician.data.remote.apiservice.ApiService
import com.elfiltar.elfiltartechnician.data.remote.apiservice.MyData
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AppRepository @Inject constructor(private val apiService: ApiService) {


    /*************************** Authentication *******************************/
    fun login(body: HashMap<String, Any>): Observable<MyData<ProfileModel>> {
        return apiService.login(body)
    }
   fun getTechnicianProfile(technicianId: Int): Observable<MyData<ProfileModel>> {
        return apiService.getTechnicianProfile(technicianId)
    }

    fun registerAsTechnician(
        map: HashMap<String, RequestBody>,
        cities: ArrayList<Int>,
        governorates: ArrayList<Int>,
        image: MultipartBody.Part,
        nationality_id_image: MultipartBody.Part,
    ): Observable<MyData<ProfileModel>> {
        return apiService.registerAsTechnician(
            map,
            cities,
            governorates,
            image,
            nationality_id_image
        )
    }

    fun updateProfile(
        map: HashMap<String, RequestBody>,
        cities: ArrayList<Int>,
        governorates: ArrayList<Int>,
        image: MultipartBody.Part? = null,
        nationality_id_image: MultipartBody.Part? = null,
        tax_number_image: MultipartBody.Part? = null,
        commercial_number_image: MultipartBody.Part? = null,
    ): Observable<MyData<ProfileModel>> {
        return apiService.updateProfile(
            map,
            cities,
            governorates,
            image,
            nationality_id_image,
            tax_number_image,
            commercial_number_image
        )
    }

    fun registerAsCompany(
        map: HashMap<String, RequestBody>,
        cities: ArrayList<Int>,
        governorates: ArrayList<Int>,
        image: MultipartBody.Part,
        tax_number_image: MultipartBody.Part,
        commercial_number_image: MultipartBody.Part,
    ): Observable<MyData<ProfileModel>> {
        return apiService.registerAsCompany(
            map,
            cities,
            governorates,
            image,
            tax_number_image,
            commercial_number_image
        )
    }

    fun contactUs(
        map: HashMap<String, Any>,
    ): Observable<MyData<Any>> {
        return apiService.contactUs(map)
    }

    /*************************** General *******************************/
    fun getGovernorateWithCities(): Observable<MyData<ArrayList<GovernorateWithCitiesModel>>> {
        return apiService.getGovernorateWithCities()
    }

    fun getCountries(): Observable<MyData<ArrayList<BaseSelection>>> {
        return apiService.getCountries()
    }

    fun getGovernorates(countryId: Int): Observable<MyData<ArrayList<BaseSelection>>> {
        return apiService.getGovernorates(countryId)
    }

    fun getCities(governorateId: Int): Observable<MyData<ArrayList<BaseSelection>>> {
        return apiService.getCities(governorateId)
    }

    /*************************** Clients *******************************/
    fun getAllClients(
        map: HashMap<String, Any>
    ): Observable<MyData<ArrayList<ClientModel>>> {
        return apiService.getAllClients(map)
    }
    fun getClientDetails(
        id: String
    ): Observable<MyData<ClientModel>> {
        return apiService.getClientDetails(id)
    }

    fun getClients(
        map: HashMap<String, Any>
    ): Observable<MyData<ArrayList<ClientModel>>> {
        return apiService.getClients(map)
    }

    fun addClient(
        map: HashMap<String, Any>
    ): Observable<MyData<ClientModel>> {
        return apiService.addClient(map)
    }

    fun updateClient(
        map: HashMap<String, Any>
    ): Observable<MyData<ClientModel>> {
        return apiService.updateClient(map)
    }

    /*************************** Technician Alarms *******************************/
    fun getTechnicianAlarms(
        technicianId: Int,
        technicianClientId: Int,
    ): Observable<MyData<ArrayList<AlarmModel>>> {
        return apiService.getTechnicianAlarms(technicianId, technicianClientId)
    }

    fun addTechnicianNotifications(
        map: HashMap<String, Any>
    ): Observable<MyData<AlarmModel>> {
        return apiService.addTechnicianNotifications(map)
    }

    /*************************** Notification *******************************/
    fun getNotifications(
        technicianId: Int
    ): Observable<MyData<ArrayList<NotificationModel>>> {
        return apiService.getNotifications(technicianId)
    }

    fun unReadNotifications(
        technicianId: Int
    ): Observable<MyData<Int>> {
        return apiService.unReadNotifications(technicianId)
    }

    fun deleteAllNotifications(
        technicianId: Int
    ): Observable<MyData<Any>> {
        return apiService.deleteAllNotifications(technicianId)
    }

    /*************************** WaterQuality *******************************/
    fun getWaterQualities(
        technicianId: Int
    ): Observable<MyData<ArrayList<BaseModel>>> {
        return apiService.getWaterQualities(technicianId)
    }

    fun getFilterSettings(
        technicianId: Int
    ): Observable<MyData<ArrayList<WaterQualityModel>>> {
        return apiService.getFilterSettings(technicianId)
    }

    fun createWaterQuality(
        any: Any
    ): Observable<MyData<Any>> {
        return apiService.createWaterQuality(any)
    }


    fun technicianReport(
        technicianId: Int
    ): Observable<MyData<ReportModel>> {
        return apiService.technicianReport(technicianId)
    }

    fun createCandle(
        any: HashMap<String, Any>
    ): Observable<MyData<Any>> {
        return apiService.createCandle(any)
    }

    fun updateWaterQuality(
        id: Int,
        any: HashMap<String, Any>
    ): Observable<MyData<Any>> {
        return apiService.updateWaterQuality(id, any)
    }

    fun updateCandle(
        id: Int,
        any: HashMap<String, Any>
    ): Observable<MyData<Any>> {
        return apiService.updateCandle(id, any)
    }

    fun deleteWaterQuality(
        id: Int
    ): Observable<MyData<Any>> {
        return apiService.deleteWaterQuality(id)
    }

    /*************************** Packages *******************************/
    fun getPackages(): Observable<MyData<ArrayList<PackageModel>>> {
        return apiService.getPackages()
    }

    fun subscribePackage(
        technicianId: Int,
        packageId: Int
    ): Observable<MyData<String>> {
        return apiService.subscribePackage(technicianId, packageId)
    }

    /*************************** Order *******************************/
    fun getOrders(
        technicianId: Int,
        type: String,
    ): Observable<MyData<ArrayList<OrderModel>>> {
        return apiService.getOrders(technicianId, type)
    }

    fun getOrderDetails(
        orderId: Int,
    ): Observable<MyData<OrderModel>> {
        return apiService.getOrderDetails(orderId)
    }

    fun acceptOrder(
        orderId: Int,
    ): Observable<MyData<OrderDetailsModel>> {
        return apiService.changeOrderStatus(orderId, MyConstants.Enums.OrderStatus.confirmed)
    }

    fun cancelOrder(
        orderId: Int,
        text: String,
    ): Observable<MyData<OrderDetailsModel>> {
        return apiService.changeOrderStatus(orderId, text, MyConstants.Enums.OrderStatus.cancel)
    }

    fun orderOnTheWay(
        orderId: Int,
    ): Observable<MyData<OrderDetailsModel>> {
        return apiService.changeOrderStatus(orderId, MyConstants.Enums.OrderStatus.on_way)
    }

    fun finishOrder(
        orderId: Int,
        price: Double,
    ): Observable<MyData<OrderDetailsModel>> {
        return apiService.finishOrder(orderId, MyConstants.Enums.OrderStatus.end, price)
    }

}