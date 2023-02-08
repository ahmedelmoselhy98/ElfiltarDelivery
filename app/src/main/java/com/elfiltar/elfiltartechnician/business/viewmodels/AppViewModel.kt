package com.elfiltar.elfiltartechnician.business.viewmodels

import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.base.BaseViewModel
import com.elfiltar.elfiltartechnician.business.authentication.models.ProfileModel
import com.elfiltar.elfiltartechnician.business.delivery.models.*
import com.elfiltar.elfiltartechnician.business.general.models.NotificationModel
import com.elfiltar.elfiltartechnician.business.repositories.AppRepository
import com.elfiltar.elfiltartechnician.commons.helpers.MyConstants
import com.elfiltar.elfiltartechnician.commons.models.BaseSelection
import com.elfiltar.elfiltartechnician.commons.rx.CustomRxObserver
import com.elfiltar.elfiltartechnician.data.local.session.SessionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AppViewModel @Inject constructor(
    private val authenticationRepository: AppRepository,
) : BaseViewModel() {

    @Inject
    lateinit var sessionHelper: SessionHelper

    fun login(
        phone: String, phoneCode: String, onResult: (ProfileModel) -> Unit
    ) {
        var bodyMap = HashMap<String, Any>()
        bodyMap["phone"] = phone
        bodyMap["phone_code"] = phoneCode
        authenticationRepository.login(bodyMap).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ProfileModel>(this@AppViewModel) {
                override fun onResponse(response: ProfileModel) {
                    onResult(response)
                }
            })
    }

    fun getTechnicianProfile(onResult: (ProfileModel) -> Unit) {
        authenticationRepository.getTechnicianProfile(sessionHelper.getUserSession()!!.id!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ProfileModel>(this@AppViewModel) {
                override fun onResponse(response: ProfileModel) {
                    onResult(response)
                }
            })
    }

    fun registerAsTechnician(
        map: HashMap<String, RequestBody>,
        cities: ArrayList<Int>,
        image: MultipartBody.Part,
        nationality_id_image: MultipartBody.Part,
        onResult: (ProfileModel) -> Unit
    ) {
        authenticationRepository.registerAsTechnician(
            map,
            cities,
            image,
            nationality_id_image,
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ProfileModel>(this@AppViewModel) {
                override fun onResponse(response: ProfileModel) {
                    onResult(response)
                }
            })
    }

    fun updateProfile(
        map: HashMap<String, RequestBody>,
        cities: ArrayList<Int>,
        image: MultipartBody.Part? = null,
        nationality_id_image: MultipartBody.Part? = null,
        tax_number_image: MultipartBody.Part? = null,
        commercial_number_image: MultipartBody.Part? = null,
        onResult: (ProfileModel) -> Unit
    ) {
        authenticationRepository.updateProfile(
            map,
            cities,
            image,
            nationality_id_image,
            tax_number_image,
            commercial_number_image
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ProfileModel>(this@AppViewModel) {
                override fun onResponse(response: ProfileModel) {
                    onResult(response)
                }
            })
    }

    fun registerAsCompany(
        map: HashMap<String, RequestBody>,
        cities: ArrayList<Int>,
        image: MultipartBody.Part,
        tax_number_image: MultipartBody.Part,
        commercial_number_image: MultipartBody.Part,
        onResult: (ProfileModel) -> Unit
    ) {
        authenticationRepository.registerAsCompany(
            map, cities, image, tax_number_image, commercial_number_image
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ProfileModel>(this@AppViewModel) {
                override fun onResponse(response: ProfileModel) {
                    onResult(response)
                }
            })
    }

    fun contactUs(
        map: HashMap<String, Any>,
        onResult: (Any) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        authenticationRepository.contactUs(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }


    /*************************** general *******************************/
    fun getGovernorateWithCities(
        onResult: (ArrayList<GovernorateWithCitiesModel>) -> Unit
    ) {
        authenticationRepository.getGovernorateWithCities()
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object :
                CustomRxObserver<ArrayList<GovernorateWithCitiesModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<GovernorateWithCitiesModel>) {
                    onResult(response)
                }
            })
    }

    fun getCountries(
        onResult: (ArrayList<BaseSelection>) -> Unit
    ) {
        authenticationRepository.getCountries().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<BaseSelection>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<BaseSelection>) {
                    onResult(response)
                }
            })
    }

    fun getGovernorates(
        countryId: Int, onResult: (ArrayList<BaseSelection>) -> Unit
    ) {
        authenticationRepository.getGovernorates(countryId)
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<BaseSelection>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<BaseSelection>) {
                    onResult(response)
                }
            })
    }

    fun getCities(
        governoratesId: Int, onResult: (ArrayList<BaseSelection>) -> Unit
    ) {
        authenticationRepository.getCities(governoratesId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<BaseSelection>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<BaseSelection>) {
                    onResult(response)
                }
            })
    }

    /*************************** Package *******************************/
    fun getPackages(
        onResult: (ArrayList<PackageModel>) -> Unit
    ) {
        authenticationRepository.getPackages().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<PackageModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<PackageModel>) {
                    onResult(response)
                }
            })
    }


    fun subscribePackage(
        packageId: Int, onResult: (String) -> Unit
    ) {
        authenticationRepository.subscribePackage(sessionHelper.getUserSession()!!.id!!, packageId)
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<String>(this@AppViewModel) {
                override fun onResponse(response: String) {
                    onResult(response)
                }
            })
    }

    /*************************** Technician Alarms *******************************/
    fun getTechnicianAlarms(
        technicianClientId: Int, onResult: (ArrayList<AlarmModel>) -> Unit
    ) {
        authenticationRepository.getTechnicianAlarms(
            sessionHelper.getUserSession()!!.id!!, technicianClientId
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<AlarmModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<AlarmModel>) {
                    onResult(response)
                }
            })
    }

    fun addTechnicianAlarm(
        map: HashMap<String, Any>,
        onResult: (AlarmModel) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        authenticationRepository.addTechnicianNotifications(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object :
                CustomRxObserver<AlarmModel>(this@AppViewModel) {
                override fun onResponse(response: AlarmModel) {
                    onResult(response)
                }
            })
    }


    /*************************** Notifications *******************************/
    fun getNotifications(
        onResult: (ArrayList<NotificationModel>) -> Unit
    ) {
        authenticationRepository.getNotifications(
            sessionHelper.getUserSession()!!.id!!
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<NotificationModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<NotificationModel>) {
                    onResult(response)
                }
            })
    }

    fun unReadNotifications(
        onResult: (Int) -> Unit
    ) {
        authenticationRepository.unReadNotifications(
            sessionHelper.getUserSession()!!.id!!
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Int>(this@AppViewModel) {
                override fun onResponse(response: Int) {
                    onResult(response)
                }
            })
    }

    fun deleteAllNotifications(
        onResult: (Any) -> Unit
    ) {
        authenticationRepository.deleteAllNotifications(
            sessionHelper.getUserSession()!!.id!!
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }


    /*************************** WaterQuality *******************************/
    fun getWaterQualities(
        onResult: (ArrayList<BaseModel>) -> Unit
    ) {
        authenticationRepository.getWaterQualities(sessionHelper.getUserSession()!!.id!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<BaseModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<BaseModel>) {
                    onResult(response)
                }
            })
    }

    fun getWaterQualitiesFilterSetting(
        onResult: (ArrayList<WaterQualityModel>) -> Unit
    ) {
        authenticationRepository.getFilterSettings(
            sessionHelper.getUserSession()!!.id!!
        ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<WaterQualityModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<WaterQualityModel>) {
                    onResult(response)
                }
            })
    }


    fun updateWaterQuality(
        id: Int,
        map: HashMap<String, Any>,
        onResult: (Any) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        authenticationRepository.updateWaterQuality(id, map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }

    fun updateCandle(
        id: Int,
        map: HashMap<String, Any>,
        onResult: (Any) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        authenticationRepository.updateCandle(id, map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }

    fun deleteWaterQuality(
        id: Int,
        onResult: (Any) -> Unit
    ) {
        authenticationRepository.deleteWaterQuality(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }

    fun createCandle(
        map: HashMap<String, Any>,
        onResult: (Any) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        authenticationRepository.createCandle(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }


    fun createWaterQuality(
        map: HashMap<String, Any>,
        onResult: (Any) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        authenticationRepository.createWaterQuality(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<Any>(this@AppViewModel) {
                override fun onResponse(response: Any) {
                    onResult(response)
                }
            })
    }

    fun technicianReport(
        onResult: (ReportModel) -> Unit
    ) {
        authenticationRepository.technicianReport(sessionHelper.getUserSession()!!.id!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ReportModel>(this@AppViewModel) {
                override fun onResponse(response: ReportModel) {
                    onResult(response)
                }
            })
    }

    /*************************** clients *******************************/
    fun getAllClients(
        map: HashMap<String, Any>, onResult: (ArrayList<ClientModel>) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.client
        authenticationRepository.getAllClients(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<ClientModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<ClientModel>) {
                    onResult(response)
                }
            })
    }

    fun getClients(
        map: HashMap<String, Any>, onResult: (ArrayList<ClientModel>) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.client
        authenticationRepository.getClients(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<ClientModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<ClientModel>) {
                    onResult(response)
                }
            })
    }

    fun getMaintenanceClients(
        map: HashMap<String, Any>, onResult: (ArrayList<ClientModel>) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.maintenance
        authenticationRepository.getClients(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<ClientModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<ClientModel>) {
                    onResult(response)
                }
            })
    }

    fun getAllMaintenanceClients(
        map: HashMap<String, Any>, onResult: (ArrayList<ClientModel>) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.maintenance
        authenticationRepository.getAllClients(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<ClientModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<ClientModel>) {
                    onResult(response)
                }
            })
    }

    fun addClient(
        map: HashMap<String, Any>, onResult: (ClientModel) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.client
        authenticationRepository.addClient(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ClientModel>(this@AppViewModel) {
                override fun onResponse(response: ClientModel) {
                    onResult(response)
                }
            })
    }

    fun updateClient(
        map: HashMap<String, Any>, onResult: (ClientModel) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.client
        authenticationRepository.updateClient(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ClientModel>(this@AppViewModel) {
                override fun onResponse(response: ClientModel) {
                    onResult(response)
                }
            })
    }

    fun addMaintenanceClient(
        map: HashMap<String, Any>, onResult: (ClientModel) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.maintenance
        authenticationRepository.addClient(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ClientModel>(this@AppViewModel) {
                override fun onResponse(response: ClientModel) {
                    onResult(response)
                }
            })
    }


    fun updateMaintenanceClient(
        map: HashMap<String, Any>, onResult: (ClientModel) -> Unit
    ) {
        map["technician_id"] = sessionHelper.getUserSession()!!.id!!
        map["type"] = MyConstants.Enums.ClientType.maintenance
        authenticationRepository.updateClient(map).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ClientModel>(this@AppViewModel) {
                override fun onResponse(response: ClientModel) {
                    onResult(response)
                }
            })
    }

    /*************************** Order *******************************/
    fun getOrders(
        type: String, onResult: (ArrayList<OrderModel>) -> Unit
    ) {
        authenticationRepository.getOrders(sessionHelper.getUserSession()!!.id!!, type)
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<ArrayList<OrderModel>>(this@AppViewModel) {
                override fun onResponse(response: ArrayList<OrderModel>) {
                    onResult(response)
                }
            })
    }

    fun getOrderDetails(
        orderId: Int, onResult: (OrderModel) -> Unit
    ) {
        authenticationRepository.getOrderDetails(orderId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<OrderModel>(this@AppViewModel) {
                override fun onResponse(response: OrderModel) {
                    onResult(response)
                }
            })
    }

    fun acceptOrder(
        orderId: Int, onResult: (OrderDetailsModel) -> Unit
    ) {
        authenticationRepository.acceptOrder(orderId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<OrderDetailsModel>(this@AppViewModel) {
                override fun onResponse(response: OrderDetailsModel) {
                    onResult(response)
                }
            })
    }

    fun cancelOrder(
        orderId: Int, text: String, onResult: (OrderDetailsModel) -> Unit
    ) {
        authenticationRepository.cancelOrder(orderId, text)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<OrderDetailsModel>(this@AppViewModel) {
                override fun onResponse(response: OrderDetailsModel) {
                    onResult(response)
                }
            })
    }

    fun orderOnTheWay(
        orderId: Int, onResult: (OrderDetailsModel) -> Unit
    ) {
        authenticationRepository.orderOnTheWay(orderId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<OrderDetailsModel>(this@AppViewModel) {
                override fun onResponse(response: OrderDetailsModel) {
                    onResult(response)
                }
            })
    }

    fun finishOrder(
        orderId: Int, price: Double, onResult: (OrderDetailsModel) -> Unit
    ) {
        authenticationRepository.finishOrder(orderId, price)
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CustomRxObserver<OrderDetailsModel>(this@AppViewModel) {
                override fun onResponse(response: OrderDetailsModel) {
                    onResult(response)
                }
            })
    }


}