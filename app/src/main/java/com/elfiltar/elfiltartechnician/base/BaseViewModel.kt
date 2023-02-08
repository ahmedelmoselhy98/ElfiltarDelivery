package com.elfiltar.elfiltartechnician.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
open class BaseViewModel constructor(
) : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
}