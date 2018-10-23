package com.lewis_v.yeventbus.observe

import java.util.*

//在发布的线程处理
open class YObserver<E : Any> : OnGetEvent<E> {
    override fun onSuccess(event: E) {

    }

    override fun onFail(e: Exception) {

    }

    override fun update(o: Observable?, arg: Any) {
        try {
            onSuccess(arg as E)
        } catch (e: Exception) {
            onFail(e)
        }

    }
}
