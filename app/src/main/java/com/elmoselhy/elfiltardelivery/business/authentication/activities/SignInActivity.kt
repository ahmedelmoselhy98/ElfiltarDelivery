package com.elmoselhy.elfiltardelivery.business.authentication.activities

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.business.authentication.sheet.OtpSheet
import com.elmoselhy.elfiltardelivery.business.delivery.activities.MainActivity
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.helper.ElmoselhyInputHelper
import com.elmoselhy.elfiltardelivery.commons.events.PhoneNotRegisteredEvent
import com.elmoselhy.elfiltardelivery.commons.helpers.MyUtils
import com.elmoselhy.elfiltardelivery.commons.models.UnAuthorizedErrorModel
import com.elmoselhy.elfiltardelivery.commons.rx.RxBus
import com.elmoselhy.elfiltardelivery.databinding.ActivitySignInBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import www.sanju.motiontoast.MotionToast
import java.util.concurrent.TimeUnit

class SignInActivity : BaseActivity() {

    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var storedVerificationId = ""

    //declare properties
    lateinit var binding: ActivitySignInBinding
    private val appViewModel: AppViewModel by viewModels()

    var auth = FirebaseAuth.getInstance()
    override fun setUpLayoutView(): View {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
    }

    private fun setUpPageActions() {
        binding.btnLogin.setOnClickListener {
            if (ElmoselhyInputHelper.checkIfInputsIsValid(this, getInputsUiList())) {
                startPhoneNumberVerification()
            }
        }
        eventDisposable = RxBus.listen(PhoneNotRegisteredEvent::class.java)
            .subscribe() { event ->
                startActivity(
                    Intent(this, SignUpActivity::class.java).putExtra(
                        "phone",
                        binding.etPhone.text.toString()
                    ).putExtra(
                        "phone_code",
                        binding.countryCodePicker.selectedCountryCode
                    )
                )
            }
    }

    fun getPhone(): String {
        var phone = ""
        if (binding.etPhone.text.toString().length > 1)
            phone = if (binding.etPhone.text.toString()[0] == '0') {
                binding.etPhone.text.toString().substring(1)
            } else binding.etPhone.text.toString()
        return phone
    }

    private fun login() {


        appViewModel.login(
            getPhone(),
            binding.countryCodePicker.selectedCountryCode,
            onResult = {
                if (it != null) {
                    sessionHelper.setUserSession(it)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    MyUtils.shoMsg(this, "Response data null", MotionToast.TOAST_ERROR)
                }
            }
        )
    }

    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("SignInActivity", "onVerificationCompleted:$credential")
            appViewModel.isLoading.postValue(true)
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.e("SignInActivity", "onVerificationFailed", e)
            appViewModel.isLoading.postValue(false)
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.e("SignInActivity", "onCodeSent:$verificationId")
            appViewModel.isLoading.postValue(false)

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token
            runOnUiThread {
                OtpSheet(
                    this@SignInActivity,
                    "+${binding.countryCodePicker.selectedCountryCode + getPhone()}",
                    onResendCode = {
                        resendVerificationCode(
                            "+${binding.countryCodePicker.selectedCountryCode + getPhone()}",
                            resendToken
                        )
                    },
                    onConfirm = {
                        verifyPhoneNumberWithCode(storedVerificationId, it)
                    }).show()
            }
        }
    }

    private fun startPhoneNumberVerification() {
        // [START start_phone_auth]
        appViewModel.isLoading.postValue(true)
        Log.e(
            "phone",
            "+${binding.countryCodePicker.selectedCountryCode + getPhone()}"
        )
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+${binding.countryCodePicker.selectedCountryCode + getPhone()}")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        appViewModel.isLoading.postValue(true)
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
        // [END verify_with_code]
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        appViewModel.isLoading.postValue(true)
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        appViewModel.isLoading.postValue(true)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                appViewModel.isLoading.postValue(false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("SignInActivity", "signInWithCredential:success")
                    val user = task.result?.user
                    login()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.e("SignInActivity", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun getInputsUiList(): ArrayList<BaseInput> {
        var inputsList = ArrayList<BaseInput>()
        inputsList.add(binding.etPhone)
        return inputsList
    }
}