package com.lewis_v.yeventbus

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * auth: lewis-v
 * time: 2018/3/25.
 */

object ThreadSchedule {

    val executorServiceHandle: ExecutorService by lazy {
        Executors.newCachedThreadPool()
    }//处理线程池

    val mainHandle by lazy { Handler(Looper.getMainLooper()) }
}
