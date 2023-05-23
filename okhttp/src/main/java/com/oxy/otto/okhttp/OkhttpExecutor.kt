package com.oxy.otto.okhttp

import com.oxy.otto.core.Executor
import com.oxy.otto.core.Task
import com.oxy.otto.core.TaskSource
import okhttp3.OkHttpClient
import okhttp3.Request

class OkhttpExecutor(
    private val client: OkHttpClient
) : Executor {
    override fun execute(source: TaskSource?, task: Task) {
        val request = Request.Builder()
            .url(task.data ?: error("task url cannot be null"))
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        val buffer = ByteArray(task.buffer)
        val output = task.output ?: return
        val input = response.body?.byteStream() ?: return
        var total = 0L
        var count: Int
        val contentLength = response.body?.contentLength()!!
        while (input.read(buffer).also { count = it } != -1) {
            total += count
            output.write(buffer, 0, count)
            task.receiver?.invoke(
                Task.Snapshot(total, count, contentLength)
            )
        }
        input.close()
        output.flush()
        output.close()
    }
}