package com.oxy.otto.okhttp

import com.oxy.otto.core.Task
import com.oxy.otto.core.Executor
import okhttp3.OkHttpClient
import okhttp3.Request

class OkhttpExecutor(
    private val client: OkHttpClient
) : Executor {
    override fun execute(task: Task) {
        val request = Request.Builder()
            .url(task.url ?: error("task url cannot be null"))
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        val buffer = ByteArray(task.buffer)
        val output = task.output ?: return
        val input = response.body?.byteStream() ?: return
        val total = response.body?.contentLength()!!
        var count: Int
        while (input.read(buffer).also { count = it } != -1) {
            task.receiver?.invoke(
                Task.Snapshot(total, count)
            )
            output.write(buffer)
        }
        input.close()
        output.flush()
        output.close()
    }
}