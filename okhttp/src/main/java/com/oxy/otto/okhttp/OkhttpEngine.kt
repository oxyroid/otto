package com.oxy.otto.okhttp

import com.oxy.otto.core.Engine
import com.oxy.otto.core.Executor
import okhttp3.OkHttpClient

object OkhttpEngine : Engine by OkhttpEngine(
    client = OkHttpClient.Builder().build()
)

fun OkhttpEngine(
    client: OkHttpClient
): Engine {
    return object : Engine {
        override val executor: Executor
            get() = OkhttpExecutor(client)
    }
}