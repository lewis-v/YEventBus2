package com.lewis_v.yeventbus

import android.support.v4.util.ArrayMap
import android.util.Log
import com.lewis_v.yeventbus.observe.OnGetEvent
import com.lewis_v.yeventbus.observe.YObserver
import java.util.concurrent.ConcurrentHashMap

class YObservableManager {
    val mObservableMap: ConcurrentHashMap<Class<*>, YObservable> = ConcurrentHashMap()
    val correlationMap: ConcurrentHashMap<Any, MutableMap<Class<*>, MutableList<OnGetEvent<*>>>> = ConcurrentHashMap()
    val stickyEventMap: ConcurrentHashMap<Class<*>, Any> = ConcurrentHashMap()
    val handle: IEventHandle = YEventHandle()

    /**
     * 发布消息
     **/
    fun <T : Any> postEvent(event: Class<T>, data: T) {
        val observables = mObservableMap[event]
        observables?.let {
            handle.postEvent<Any>(it, data)
        }
    }

    /**
     * 发布粘性事件
     */
    fun <T : Any> postStickyEvent(event: Class<T>, data: T) {
        postEvent(event, data)
        stickyEventMap[event] = data
    }

    /**
     * 移除一个粘性事件
     */
    @Synchronized
    fun <T : Any> removeStickyEvent(clazz: Class<T>) {
        stickyEventMap.remove(clazz)
    }

    /**
     * 移除所有粘性事件
     */
    @Synchronized
    fun removeAllStickyEvent() {
        stickyEventMap.clear()
    }

    /**
     * 订阅事件
     **/
    @Synchronized
    fun <T : Any> subscriber(owner: Any, clazz: Class<T>, observer: OnGetEvent<T>) {
        if (mObservableMap.containsKey(clazz)) {
            mObservableMap[clazz]?.addObserver(observer)
        } else {
            val observable = YObservable()
            observable.addObserver(observer)
            mObservableMap[clazz] = observable
        }

        var map = correlationMap[owner]
        if (map != null){
            val eventList = map[clazz]
            if (eventList != null){
                eventList.add(observer)
            } else {
                val list :MutableList<OnGetEvent<*>> = ArrayList()
                list.add(observer)
                map[clazz] = list
            }
        } else {
            map = ArrayMap()
            val list :MutableList<OnGetEvent<*>> = ArrayList()
            list.add(observer)
            map[clazz] = list
            correlationMap[owner] = map
        }

        stickyEventMap[clazz]?.let {
            observer.update(null, it)
        }
    }

    /**
     * 解除订阅
     */
    @Synchronized
    fun <T : Any> unSubscriber(owner: Any, clazz: Class<T>, observer: OnGetEvent<T>) {
        mObservableMap[clazz]?.deleteObserver(observer)
        val map = correlationMap[owner]
        map?.get(clazz)?.remove(observer)
    }

    /**
     * 解除订阅
     */
    @Synchronized
    fun unSubscriber(owner: Any) {
        val map = correlationMap.remove(owner)
        if (map == null) {
            throw RuntimeException("this event ${owner::class.java} is already unSubscriber")
        } else {
            for ((clazz,events) in map){
                mObservableMap[clazz]?.let {
                    for (event in events) {
                        it.deleteObserver(event)
                    }
                    if (it.countObservers() == 0){
                        mObservableMap.remove(clazz)
                    }
                }
            }
        }
    }

    /**
     * 解除一个事件系列的订阅
     */
    @Synchronized
    fun unSubscriberEvent(event: Class<out Any>) {
        if (mObservableMap.containsKey(event)) {
            mObservableMap[event]?.deleteObservers()
            mObservableMap.remove(event)
            for (map in correlationMap.values) {
                map.remove(event)
            }
        }
    }

    /**
     * 解除所有事件订阅
     */
    @Synchronized
    fun unSubscriberAll() {
        for ((_, value) in mObservableMap) {
            value.deleteObservers()
        }
        mObservableMap.clear()
        for (value in correlationMap.values){
            value.clear()
        }
    }
}
