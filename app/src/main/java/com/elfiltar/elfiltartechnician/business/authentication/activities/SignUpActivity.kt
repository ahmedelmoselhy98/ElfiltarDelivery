package com.elfiltar.elfiltartechnician.business.authentication.activities

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.base.BaseModel
import com.elfiltar.elfiltartechnician.business.delivery.activities.MainActivity
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
import com.elfiltar.elfiltartechnician.databinding.ActivitySignUpBinding
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import okhttp3.MultipartBody
import www.sanju.motiontoast.MotionToast

class SignUpActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivitySignUpBinding
    private val appViewModel: AppViewModel by viewModels()

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


    override fun setUpLayoutView(): View {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        registerBody["type"] = MyConstants.Enums.UserType.company
        if (intent.getStringExtra("phone") != null) binding.etPhone.setText(intent.getStringExtra("phone")!!)
        if (intent.getStringExtra("phone_code") != null) binding.countryCodePicker.setCountryForPhoneCode(
            intent.getStringExtra("phone_code")!!.toInt()
        )
        setUpPageActions()
        setUpGovernoratesList()
        setUpCitiesList()
        setUpObserver()
    }

    fun getPhone(): String {
        var phone = ""
        if (binding.etPhone.text.toString().length > 1) phone =
            if (binding.etPhone.text.toString()[0] == '0') {
                binding.etPhone.text.toString().substring(1)
            } else binding.etPhone.text.toString()
        return phone
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnStart.setOnClickListener {
            if (!ElmoselhyInputHelper.checkIfInputsIsValid(
                    this, getInputsUiList()
                )
            ) return@setOnClickListener
            else if (image == null) {
                MyUtils.shoMsg(this, getString(R.string.profile_image), MotionToast.TOAST_ERROR)
                return@setOnClickListener
            } else if (registerBody["type"] == MyConstants.Enums.UserType.company) {
                if (tax_number_image == null) {
                    MyUtils.shoMsg(this, getString(R.string.tax_registry_image), MotionToast.TOAST_ERROR)
                    return@setOnClickListener
                } else if (commercial_number_image == null) {
                    MyUtils.shoMsg(
                        this, getString(R.string.commercial_registry_image), MotionToast.TOAST_ERROR
                    )
                    return@setOnClickListener
                }
            } else {
                if (nationality_id_image == null) {
                    MyUtils.shoMsg(
                        this, getString(R.string.identity_image), MotionToast.TOAST_ERROR
                    )
                    return@setOnClickListener
                }
            }
            if (registerBody["governorate_id"] == null) {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.governorate) + " " + getString(R.string.error_message_required),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            }
            if (governorates.isNullOrEmpty()) {
                MyUtils.shoMsg(
                    this,
                    getString(R.string.selected_governorates) + " " + getString(R.string.error_message_required),
                    MotionToast.TOAST_ERROR
                )
                return@setOnClickListener
            }
            register()
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
        binding.tvCompany.setOnClickListener {
            registerBody["type"] = MyConstants.Enums.UserType.company
            nationality_id_image = null
            binding.sectionCompanyImages.visibility = View.VISIBLE
            binding.sectionTechnicalImages.visibility = View.GONE
            binding.tvCompany.setBackgroundResource(R.drawable.background_solid_rectangle_primary)
            binding.tvCompany.setTextColor(ContextCompat.getColor(this, R.color.text_color_white))
            binding.tvTechnical.setBackgroundResource(R.drawable.background_stroke_rectangle_grey)
            binding.tvTechnical.setTextColor(ContextCompat.getColor(this, R.color.text_color_grey))
            binding.tvName.text = getString(R.string.company_name)
            binding.etName.hint = getString(R.string.company_name)
        }
        binding.tvTechnical.setOnClickListener {
            registerBody["type"] = MyConstants.Enums.UserType.technician
            commercial_number_image = null
            tax_number_image = null
            binding.sectionCompanyImages.visibility = View.GONE
            binding.sectionTechnicalImages.visibility = View.VISIBLE
            binding.tvCompany.setBackgroundResource(R.drawable.background_stroke_rectangle_grey)
            binding.tvCompany.setTextColor(ContextCompat.getColor(this, R.color.text_color_grey))
            binding.tvTechnical.setBackgroundResource(R.drawable.background_solid_rectangle_primary)
            binding.tvTechnical.setTextColor(ContextCompat.getColor(this, R.color.text_color_white))
            binding.tvName.text = getString(R.string.technical_name)
            binding.etName.hint = getString(R.string.technical_name)
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

    private fun register() {
        registerBody["name"] = binding.etName.text.toString()
        registerBody["phone_code"] = binding.countryCodePicker.selectedCountryCode
        registerBody["phone"] = getPhone()
        registerBody["address"] = binding.etLocation.text.toString()
        if (registerBody["type"] == MyConstants.Enums.UserType.technician) appViewModel.registerAsTechnician(
            MyUtils.toRequestBodyMap(registerBody),
            cities,
            governorates,
            image!!,
            nationality_id_image!!,
            onResult = {
                sessionHelper.setUserSession(it)
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            })
        else {
            appViewModel.registerAsCompany(MyUtils.toRequestBodyMap(registerBody),
                cities,
                governorates,
                image!!,
                tax_number_image!!,
                commercial_number_image!!,
                onResult = {
                    sessionHelper.setUserSession(it)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                })
        }

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
        })
    }

    private fun setUpGovernorateSelection() {
        appViewModel.getGovernorateWithCities(onResult = { list ->
            UtilsCustomExpandable.setUpExpandList(this,
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
                    if (!list[position].cities.isNullOrEmpty()) setUpCitySelection(list[position].cities!!)
                },
                onItemUnSelected = { position, item ->
                    governorateList.remove(list[position])
                    governorates.remove(list[position].id!!)
                    governorateAdapter.replaceDataList(governorateList)
                    setUpCitySelection(ArrayList())
                })
        })
    }

    private fun setUpCitySelection(list: ArrayList<BaseModel>) {
        UtilsCustomExpandable.setUpExpandList(this,
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
            })
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
        inputsList.add(binding.etName)
        inputsList.add(binding.etPhone)
        inputsList.add(binding.etLocation)
        return inputsList
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        CheckPermissionsHelper.handleCameraPermissionResult(
            this,
            permissions,
            grantResults,
            onPermissionGranted = {
                selectPicture()
            })
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}