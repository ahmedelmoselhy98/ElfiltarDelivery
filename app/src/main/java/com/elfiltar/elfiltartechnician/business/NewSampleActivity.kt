package com.elfiltar.elfiltartechnician.business

import android.view.View
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.databinding.ElmoselhyActivitySampleBinding

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