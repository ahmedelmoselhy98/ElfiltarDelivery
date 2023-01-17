package com.elmoselhy.elfiltardelivery.business

import android.view.View
import com.elmoselhy.elfiltardelivery.base.BaseActivity
import com.elmoselhy.elfiltardelivery.databinding.ElmoselhyActivitySampleBinding

class NewSampleActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ElmoselhyActivitySampleBinding

    override fun setUpLayoutView(): View {
        binding = ElmoselhyActivitySampleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        setUpPageActions()
    }

    private fun setUpPageActions() {
        binding.appBar.tvPageTitle.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}