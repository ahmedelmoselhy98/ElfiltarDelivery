package com.elmoselhy.elfiltardelivery.business.authentication.activities

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.base.BaseModel
import com.elmoselhy.elfiltardelivery.business.authentication.models.ProfileModel
import com.elmoselhy.elfiltardelivery.business.delivery.adapters.SelectedCityAdapter
import com.elmoselhy.elfiltardelivery.business.general.dialog.CameraGalleryDialog
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elmoselhy.elfiltardelivery.commons.helpers.CameraGalleryHelper
import com.elmoselhy.elfiltardelivery.commons.helpers.CheckPermissionsHelper
import com.elmoselhy.elfiltardelivery.commons.helpers.MyConstants
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.databinding.ActivityProfileBinding
import okhttp3.MultipartBody
import www.sanju.motiontoast.MotionToast

class ProfileActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityProfileBinding
    private val appViewModel: AppViewModel by viewModels()

    lateinit var adapter: SelectedCityAdapter
    var dataList = ArrayList<BaseModel>()
    var cities = ArrayList<Int>()
    var registerBody = HashMap<String, Any>()

    var image: MultipartBody.Part? = null
    var tax_number_image: MultipartBody.Part? = null
    var commercial_number_image: MultipartBody.Part? = null
    var nationality_id_image: MultipartBody.Part? = null
    var imageTyp = ""
    var profile = ProfileModel()
    override fun setUpLayoutView(): View {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        profile = sessionHelper.getUserSession()!!
        binding.profile = profile
        if (profile.type == MyConstants.Enums.UserType.company) {
            binding.sectionCompanyImages.visibility = View.VISIBLE
            binding.sectionTechnicalImages.visibility = View.GONE
            binding.tvName.text = getString(R.string.company_name)
            binding.etName.hint = getString(R.string.company_name)
        } else {
            binding.sectionCompanyImages.visibility = View.GONE
            binding.sectionTechnicalImages.visibility = View.VISIBLE
            binding.tvName.text = getString(R.string.technical_name)
            binding.etName.hint = getString(R.string.technical_name)
        }
        registerBody["technician_id"] = profile.id!!
        registerBody["name"] = profile.name!!
        registerBody["address"] = profile.address!!
        registerBody["phone"] = profile.phone!!
        registerBody["phone_code"] = profile.phone_code!!
        registerBody["governorate_id"] = profile.governorate_id!!
        binding.includeSelectionGovernorate.tvTitle.text = profile.governorate!!.title
        dataList.clear()
        dataList.addAll(profile.cities!!)
//        registerBody["governorate_id"] =profile.governorate_id!!
        setUpPageActions()
        setUpList()
        setUpObserver()
    }

    private fun setUpPageActions() {

        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnEdit.setOnClickListener {
            if (!ElmoselhyInputHelper.checkIfInputsIsValid(
                    this, getInputsUiList()
                )
            ) return@setOnClickListener
            update()
        }
        binding.ivProfile.setOnClickListener {
            imageTyp = "image"
            selectPicture()
        }
        binding.ivIdentityImage.setOnClickListener {
            imageTyp = "nationality_id_image"
            selectPicture()
        }
        binding.ivTaxRegistryImage.setOnClickListener {
            imageTyp = "tax_number_image"
            selectPicture()
        }
        binding.ivCommercialRegistryImage.setOnClickListener {
            imageTyp = "commercial_number_image"
            selectPicture()
        }

    }

    private fun setUpObserver() {
        setUpGovernorateSelection()
    }

    private fun selectPicture() {
        if (CheckPermissionsHelper.isCameraPermissionGranted(this)) {
            appViewModel.isLoading.postValue(true)
            CameraGalleryDialog(this, click = {
                appViewModel.isLoading.postValue(false)
            }).show()
        }
    }

    private fun update() {
        registerBody["name"] = binding.etName.text.toString()
        registerBody["phone_code"] = binding.countryCodePicker.selectedCountryCode
        registerBody["phone"] = getPhone()
        registerBody["address"] = binding.etLocation.text.toString()
        cities.clear()
        dataList.forEach {
            cities.add(it.id!!)
        }
        appViewModel.updateProfile(
            MyUtils.toRequestBodyMap(registerBody),
            cities,
            image,
            nationality_id_image,
            tax_number_image,
            commercial_number_image,
            onResult = {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.success),
                    MotionToast.TOAST_SUCCESS
                )
                sessionHelper.setUserSession(it)
            })
    }

    private fun setUpGovernorateSelection() {
        appViewModel.getGovernorateWithCities(onResult = { list ->
            UtilsCustomExpandable.setUpExpandList(
                this,
                binding.includeSelectionGovernorate.recyclerView,
                list,
                binding.includeSelectionGovernorate.viewExpand,
                getString(R.string.governorate),
                binding.includeSelectionGovernorate.tvTitle,
                binding.includeSelectionGovernorate.tvEmpty,
                binding.includeSelectionGovernorate.etSearch,
                0,
                onItemSelected = { position, item ->
                    registerBody["governorate_id"] = list[position].id!!
                    if (!list[position].cities.isNullOrEmpty())
                        setUpCitySelection(list[position].cities!!)
                    registerBody["governorate_id"] = list[position].id!!
                    if (!list[position].cities.isNullOrEmpty())
                        setUpCitySelection(list[position].cities!!)
                },
                onItemUnSelected = { position, item ->
                    registerBody.remove("governorate_id")
                    setUpCitySelection(ArrayList())
                }
            )
        })
    }

    fun getPhone(): String {
        var phone = ""
        if (binding.etPhone.text.toString().length > 1)
            phone = if (binding.etPhone.text.toString()[0] == '0') {
                binding.etPhone.text.toString().substring(1)
            } else binding.etPhone.text.toString()
        return phone
    }
    private fun setUpCitySelection(list: ArrayList<BaseModel>) {
        UtilsCustomExpandable.setUpExpandList(
            this,
            binding.includeSelectionCity.recyclerView,
            list,
            binding.includeSelectionCity.viewExpand,
            getString(R.string.city),
            binding.includeSelectionCity.tvTitle,
            binding.includeSelectionCity.tvEmpty,
            binding.includeSelectionCity.etSearch,
            0,
            onItemSelected = { position, item ->
                dataList.add(list[position])
                cities.add(list[position].id!!)
                adapter.replaceDataList(dataList)
            },
            onItemUnSelected = { position, item ->
                dataList.remove(list[position])
                cities.remove(list[position].id!!)
                adapter.replaceDataList(dataList)
            }
        )
    }

    private fun setUpList() {
        adapter = SelectedCityAdapter(dataList) { position, orderModel ->
            dataList.remove(orderModel)
            adapter.removeItem(position)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etPhone)
        return inputsList
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        CheckPermissionsHelper.handleCameraPermissionResult(this,
            permissions,
            grantResults,
            onPermissionGranted = {
                selectPicture()
            })
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && data.data != null)
            when (imageTyp) {
                "image" -> {
                    image = CameraGalleryHelper.getImageSelectionMultipart(
                        this, data.data!!, "image"
                    )
                    binding.ivProfile.setImageURI(data.data)
                }
                "nationality_id_image" -> {
                    nationality_id_image = CameraGalleryHelper.getImageSelectionMultipart(
                        this, data.data!!, "nationality_id_image"
                    )
                    binding.ivIdentityImage.setImageURI(data.data)
                }
                "tax_number_image" -> {
                    tax_number_image = CameraGalleryHelper.getImageSelectionMultipart(
                        this, data.data!!, "tax_number_image"
                    )
                    binding.ivTaxRegistryImage.setImageURI(data.data)
                }
                "commercial_number_image" -> {
                    commercial_number_image = CameraGalleryHelper.getImageSelectionMultipart(
                        this, data.data!!, "commercial_number_image"
                    )
                    binding.ivCommercialRegistryImage.setImageURI(data.data)
                }
            }
        appViewModel.isLoading.postValue(false)
    }
}