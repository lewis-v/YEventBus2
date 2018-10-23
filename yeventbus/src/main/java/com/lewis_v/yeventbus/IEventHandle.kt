package com.lewis_v.yeventbus

interface IEventHandle {
    fun <T : Any> postEvent(observable: YObservable, data: T)
}
