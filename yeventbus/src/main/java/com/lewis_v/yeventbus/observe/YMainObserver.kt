package com.lewis_v.yeventbus.observe

import android.os.Looper
import com.lewis_v.yeventbus.ThreadSchedule
import java.util.*

/**
 * 如果发布此事件在主线程就直接在主线程处理,否则会post到主线程中
 * auth: lewis-v
 * time: 2018/3/24.
 */

open class YMainObserver<E : Any> : OnGetEvent<E> {
    override fun onSuccess(event: E) {

    }

    override fun onFail(e: Exception) {

    }

    override fun update(o: Observable?, arg: Any) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            ThreadSchedule.mainHandle.post { update(o, arg) }
        }
        try {
            onSuccess(arg as E)
        } catch (e: Exception) {
            onFail(e)
        }
    }
}
