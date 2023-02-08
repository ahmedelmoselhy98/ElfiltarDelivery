package com.elfiltar.elfiltartechnician.business.delivery.activities

import android.view.View
import androidx.activity.viewModels
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.business.viewmodels.AppViewModel
import com.elfiltar.elfiltartechnician.databinding.ActivityPaymentBinding

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