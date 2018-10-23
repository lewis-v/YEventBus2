package com.lewis_v.yeventbus

import java.util.*
import kotlin.collections.ArrayList

class YObservable : Observable() {
    private val eventList: MutableList<Observer> = ArrayList()

    fun <T : Any> postEvent(data: T) {
        setChanged()
        notifyObservers(data)
    }

    override fun addObserver(o: Observer) {
        if (eventList.contains(o)) {
            throw RuntimeException("this event ${o::class.java} is already subscriber")
        }
        super.addObserver(o)
        eventList.add(o)
    }

    override fun deleteObserver(o: Observer) {
        if (!eventList.contains(o)) {
            throw RuntimeException("this event ${o::class.java} is already unSubscriber")
        }
        super.deleteObserver(o)
        eventList.remove(o)
    }
}
