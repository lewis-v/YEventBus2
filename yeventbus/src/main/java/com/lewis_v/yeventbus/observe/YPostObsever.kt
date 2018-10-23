package com.lewis_v.yeventbus.observe

import com.lewis_v.yeventbus.ThreadSchedule
import java.util.*

/**
 * company:52TT
 * data:2018/10/22
 * auth:lewis_v
 */
open class YPostObsever<E : Any> : OnGetEvent<E> {
    override fun onSuccess(event: E) {

    }

    override fun onFail(e: Exception) {

    }

    override fun update(o: Observable?, arg: Any) {
        ThreadSchedule.executorServiceHandle.execute {
            try {
                onSuccess(arg as E)
            } catch (e: Exception) {
                onFail(e)
            }
        }
    }
}