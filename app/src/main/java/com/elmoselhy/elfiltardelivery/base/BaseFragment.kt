package com.elmoselhy.elfiltardelivery.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.elmoselhy.elfiltardelivery.data.local.session.SessionHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes val resId: Int?) : Fragment() {
    @Inject
    lateinit var sessionHelper: SessionHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return resId?.let {
            inflater.inflate(resId, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayoutView()
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    protected abstract fun setUpLayoutView()
    protected abstract fun init()

}