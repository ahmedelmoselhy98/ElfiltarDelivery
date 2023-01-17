package com.elmoselhy.elfiltardelivery.business.delivery.activities

import android.content.Intent
import android.view.View
import android.webkit.WebSettings
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmoselhy.elfiltardelivery.R
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.business.delivery.adapters.ClientAdapter
import com.elmoselhy.elfiltardelivery.business.delivery.models.ClientModel
import com.elmoselhy.elfiltardelivery.business.delivery.models.GovernorateWithCitiesModel
import com.elmoselhy.elfiltardelivery.business.delivery.sheets.ClientsFilterSheet
import com.elmoselhy.elfiltardelivery.business.viewmodels.AppViewModel
import com.elmoselhy.elfiltardelivery.commons.customcomponent.editText.normal.BaseInput
import com.elmoselhy.elfiltardelivery.databinding.ActivityAllClientsBinding
import com.elmoselhy.elfiltardelivery.databinding.ActivityClientsBinding
import com.elmoselhy.elfiltardelivery.databinding.ActivityPaymentBinding
import com.google.gson.Gson

class PaymentActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityPaymentBinding
    private val appViewModel: AppViewModel by viewModels()
    override fun setUpLayoutView(): View {
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        binding.viewModel = appViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun init() {
        setUpPageActions()
    }

    private fun setUpPageActions() {
//        binding.webView.settings.javaScriptEnabled = true
//        binding.webView.settings.loadWithOverviewMode = true
//        binding.webView.settings.useWideViewPort = true
//        binding.webView.settings.builtInZoomControls = true
//        binding.webView.settings.pluginState = WebSettings.PluginState.ON
//        binding.webView.webViewClient =
//            ClientWebView(
//                this,
//                intent.getIntExtra("sec", 5), intent.getStringExtra("url").toString() + ""
//            )
        binding.webView.loadUrl(intent.getStringExtra("url")!!)
    }
}