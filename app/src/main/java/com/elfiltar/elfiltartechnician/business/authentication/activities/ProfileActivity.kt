package com.elfiltar.elfiltartechnician.business.authentication.activities

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.business.authentication.models.ProfileModel
import com.elfiltar.elfiltartechnician.business.delivery.adapters.SelectedCityAdapter
import com.elfiltar.elfiltartechnician.business.general.dialog.CameraGalleryDialog
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.commons.customcomponent.dropdownexpandablerecycler.UtilsCustomExpandable
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.BaseInput
import com.elfiltar.elfiltartechnician.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elfiltar.elfiltartechnician.commons.helpers.CameraGalleryHelper
import com.elfiltar.elfiltartechnician.commons.helpers.CheckPermissionsHelper
import com.elfiltar.elfiltartechnician.commons.helpers.MyConstants
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityProfileBinding
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import okhttp3.MultipartBody
import www.sanju.motiontoast.MotionToast

class ProfileActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityProfileBinding
    private val appViewModel: AppViewModel by viewModels()

    lateinit var adapter: SelectedCityAdapter
    lateinit var citiesAdapter: SelectedCityAdapter
    private var citiesList = ArrayList<BaseModel>()
    var cities = ArrayList<Int>()
    var registerBody = HashMap<String, Any>()

    lateinit var governorateAdapter: SelectedCityAdapter
    private var governorateList = ArrayList<BaseModel>()
    var governorates = ArrayList<Int>()

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
        citiesList.clear()
        citiesList.addAll(profile.cities!!)
        governorateList.clear()
        governorateList.addAll(profile.governorates!!)
        setUpPageActions()
        setUpGovernoratesList()
        setUpCitiesList()
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
        setUpMyGovernorateSelection()
    }

    val launcher = registerImagePicker { images ->
        // Selected images are ready to use
        appViewModel.isLoading.postValue(false)
        if (!images.isNullOrEmpty()) {
            images[0]?.let {
                    handleImages(it.uri)
            }
        }

    }

    private fun handleImages(uri: Uri) {
        when (imageTyp) {
            "image" -> {
                image = CameraGalleryHelper.getImageSelectionMultipart(
                    this, uri, "image"
                )
                binding.ivProfile.setImageURI(uri)
            }
            "nationality_id_image" -> {
                nationality_id_image = CameraGalleryHelper.getImageSelectionMultipart(
                    this, uri, "nationality_id_image"
                )
                binding.ivIdentityImage.setImageURI(uri)
            }
            "tax_number_image" -> {
                tax_number_image = CameraGalleryHelper.getImageSelectionMultipart(
                    this, uri, "tax_number_image"
                )
                binding.ivTaxRegistryImage.setImageURI(uri)
            }
            "commercial_number_image" -> {
                commercial_number_image = CameraGalleryHelper.getImageSelectionMultipart(
                    this, uri, "commercial_number_image"
                )
                binding.ivCommercialRegistryImage.setImageURI(uri)
            }
        }
    }

    private fun selectPicture() {
        if (CheckPermissionsHelper.isCameraPermissionGranted(this)) {
            appViewModel.isLoading.postValue(true)
            CameraGalleryHelper.openImagesPicker(this, launcher)
        }
    }

    private fun update() {
        registerBody["name"] = binding.etName.text.toString()
        registerBody["phone_code"] = binding.countryCodePicker.selectedCountryCode
        registerBody["phone"] = getPhone()
        registerBody["address"] = binding.etLocation.text.toString()
        cities.clear()
        citiesList.forEach {
            cities.add(it.id!!)
        }
        governorates.clear()
        governorateList.forEach {
            governorates.add(it.id!!)
        }
        appViewModel.updateProfile(
            MyUtils.toRequestBodyMap(registerBody),
            cities,
            governorates,
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


    fun getPhone(): String {
        var phone = ""
        if (binding.etPhone.text.toString().length > 1)
            phone = if (binding.etPhone.text.toString()[0] == '0') {
                binding.etPhone.text.toString().substring(1)
            } else binding.etPhone.text.toString()
        return phone
    }


    private fun setUpMyGovernorateSelection() {
        appViewModel.getGovernorateWithCities(onResult = { list ->
            UtilsCustomExpandable.setUpExpandList(this,
                binding.includeSelectionMyGovernorate.recyclerView,
                list,
                binding.includeSelectionMyGovernorate.viewExpand,
                getString(R.string.governorate),
                binding.includeSelectionMyGovernorate.tvTitle,
                binding.includeSelectionMyGovernorate.tvEmpty,
                binding.includeSelectionMyGovernorate.etSearch,
                0,
                onItemSelected = { position, item ->
                    registerBody["governorate_id"] = item.id!!
                },
                onItemUnSelected = { position, item ->
                    registerBody.remove("governorate_id")
                })
            profile.governorate?.let {
                binding.includeSelectionMyGovernorate.tvTitle.text = it.title
            }
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
                    governorateList.add(list[position])
                    governorates.add(list[position].id!!)
                    governorateAdapter.replaceDataList(governorateList)
                    if (!list[position].cities.isNullOrEmpty())
                        setUpCitySelection(list[position].cities!!)
                },
                onItemUnSelected = { position, item ->
                    governorateList.remove(list[position])
                    governorates.remove(list[position].id!!)
                    governorateAdapter.replaceDataList(governorateList)
                    setUpCitySelection(ArrayList())
                }
            )
        })
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
                citiesList.add(list[position])
                cities.add(list[position].id!!)
                citiesAdapter.replaceDataList(citiesList)
            },
            onItemUnSelected = { position, item ->
                citiesList.remove(list[position])
                cities.remove(list[position].id!!)
                citiesAdapter.replaceDataList(citiesList)
            }
        )
    }

    private fun setUpGovernoratesList() {
        governorateAdapter = SelectedCityAdapter(governorateList) { position, orderModel ->
            governorateList.remove(orderModel)
            governorateAdapter.removeItem(position)
        }
        binding.governoratesRecyclerView.adapter = governorateAdapter
        binding.governoratesRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun setUpCitiesList() {
        citiesAdapter = SelectedCityAdapter(citiesList) { position, orderModel ->
            citiesList.remove(orderModel)
            citiesAdapter.removeItem(position)
        }
        binding.citiesRecyclerView.adapter = citiesAdapter
        binding.citiesRecyclerView.layoutManager = GridLayoutManager(this, 3)
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

}