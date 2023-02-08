package com.elfiltar.elfiltartechnician.business.authentication.activities

import android.content.Intent
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
import okhttp3.MultipartBody
import www.sanju.motiontoast.MotionToast

class SignUpActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivitySignUpBinding
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


    override fun setUpLayoutView(): View {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        registerBody["type"] = MyConstants.Enums.UserType.company
        if (intent.getStringExtra("phone") != null)
            binding.etPhone.setText(intent.getStringExtra("phone")!!)
        if (intent.getStringExtra("phone_code") != null)
            binding.countryCodePicker.setCountryForPhoneCode(
                intent.getStringExtra("phone_code")!!.toInt()
            )
        setUpPageActions()
        setUpList()
        setUpObserver()
    }

    fun getPhone(): String {
        var phone = ""
        if (binding.etPhone.text.toString().length > 1)
            phone = if (binding.etPhone.text.toString()[0] == '0') {
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
            if (image == null) {
                MyUtils.shoMsg(this, getString(R.string.profile_image), MotionToast.TOAST_ERROR)
                return@setOnClickListener
            }
            if (registerBody["type"] == MyConstants.Enums.UserType.company) {
                if (tax_number_image == null) {
                    MyUtils.shoMsg(
                        this, getString(R.string.tax_registry_image), MotionToast.TOAST_ERROR
                    )
                    return@setOnClickListener
                }
                if (commercial_number_image == null) {
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
    }

    private fun selectPicture() {
        if (CheckPermissionsHelper.isCameraPermissionGranted(this)) {
            appViewModel.isLoading.postValue(true)
            CameraGalleryDialog(this, click = {
                appViewModel.isLoading.postValue(false)
            }).show()
        }
    }

    private fun register() {
        registerBody["name"] = binding.etName.text.toString()
        registerBody["phone_code"] = binding.countryCodePicker.selectedCountryCode
        registerBody["phone"] = getPhone()
        registerBody["address"] = binding.etLocation.text.toString()
        registerBody["cities"] = cities
        if (registerBody["type"] == MyConstants.Enums.UserType.technician)
            appViewModel.registerAsTechnician(
                MyUtils.toRequestBodyMap(registerBody),
                cities,
                image!!,
                nationality_id_image!!,
                onResult = {
                    sessionHelper.setUserSession(it)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                })
        else {
            appViewModel.registerAsCompany(
                MyUtils.toRequestBodyMap(registerBody),
                cities,
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
        inputsList.add(binding.etName)
        inputsList.add(binding.etPhone)
        inputsList.add(binding.etLocation)
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
        if (data != null && data.data != null) when (imageTyp) {
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