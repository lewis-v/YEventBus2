package com.lewis_v.yeventbus

class YEventHandle : IEventHandle {


    /**
     * 发布消息
     * @param observable
     * @param data
     * @param <T>
     * @throws InterruptedException
    </T> */
    override fun <T : Any> postEvent(observable: YObservable, data: T) {
        handle(observable, data)
    }

    /**
     * 在当前程处理
     * @param observable
     * @param data
     * @param <T>
    </T> */
    private fun <T : Any> handle(observable: YObservable, data: T) {
        observable.postEvent(data)
    }
}
