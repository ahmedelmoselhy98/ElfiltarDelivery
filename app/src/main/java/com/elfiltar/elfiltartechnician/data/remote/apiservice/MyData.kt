package com.elfiltar.elfiltartechnician.data.remote.apiservice

import java.io.Serializable

class MyData<T> : Serializable {
    val data: T? = null
    val msg: List<String>? = null
    val status: Int? = null
}