package com.lewis_v.yeventbus.observe

import java.util.*

interface OnGetEvent<E : Any> : Observer {
    fun onSuccess(event: E)
    fun onFail(e: Exception)
}
