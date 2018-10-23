package com.lewis_v.yeventbus

import com.lewis_v.yeventbus.observe.OnGetEvent
import com.lewis_v.yeventbus.observe.YObserver

class YEventBus private constructor() {
    val manager by lazy { YObservableManager() }

    /**
     * 订阅事件
     */
    inline fun <reified T : Any> subscriber(owner: Any, observer: OnGetEvent<T>) {
        manager.subscriber(owner, T::class.java, observer)
    }

    /**
     * 发布消息
     */
    inline fun <reified T : Any> postEvent(data: T) {
        manager.postEvent(T::class.java, data)
    }

    /**
     * 发布粘性事件
     */
    inline fun <reified T : Any> postStickyEvent(data: T) {
        manager.postStickyEvent(T::class.java,data)
    }

    /**
     * 解除订阅
     */
    inline fun <reified T : Any> unSubscriber(owner: Any, observer: OnGetEvent<T>) {
        manager.unSubscriber(owner, T::class.java, observer)
    }

    /**
     * 解除订阅
     */
    fun unSubscriber(owner: Any) {
        manager.unSubscriber(owner)
    }

    /**
     * 解除一个事件系列的订阅
     */
    fun <T : Any> unSubscriberEvent(clazz: Class<T>) {
        manager.unSubscriberEvent(clazz)
    }

    /**
     * 移除所有事件的粘性
     */
    fun removeAllStickyEvent() {
        manager.removeAllStickyEvent()
    }

    /**
     * 移除一个事件的粘性
     */
    fun <T : Any> removeStickyEvent(clazz: Class<T>) {
        manager.removeStickyEvent(clazz)
    }

    /**
     * 解除所有事件订阅
     */
    fun unSubscriberAll() {
        manager.unSubscriberAll()
    }

    companion object {

        val instance by lazy { YEventBus() }
    }
}
