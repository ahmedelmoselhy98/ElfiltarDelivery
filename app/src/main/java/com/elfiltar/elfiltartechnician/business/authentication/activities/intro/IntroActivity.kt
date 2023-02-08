package com.elfiltar.elfiltartechnician.business.authentication.activities.intro

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.base.BaseActivity
import com.elfiltar.elfiltartechnician.commons.helpers.MyUtils
import com.elfiltar.elfiltartechnician.databinding.ActivityIntroBinding
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class IntroActivity : BaseActivity() {


    //declare properties
    lateinit var binding: ActivityIntroBinding
    lateinit var adapter: IntroAdapterAdapter
    var dataList = ArrayList<Int>()


    override fun setUpLayoutView(): View {
        binding = ActivityIntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        setUpList()
    }

    private fun setUpList() {
        dataList.add(R.drawable.intro1)
        dataList.add(R.drawable.intro2)
        dataList.add(R.drawable.intro3)
        dataList.add(R.drawable.intro4)
        dataList.add(R.drawable.intro5)
        dataList.add(R.drawable.intro6)
        binding.dotsIndicator.selectedDotColor
        adapter = IntroAdapterAdapter(dataList) { position, model ->
        }
        binding.recyclerView.adapter = adapter
        val linearLayoutManager = ZoomRecyclerLayout(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = linearLayoutManager
        MyUtils.autoScrollRecycler(binding.recyclerView,dataList.size)

    }
}