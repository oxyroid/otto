package com.oxy.otto.okhttp

import com.oxy.otto.core.Executor
import com.oxy.otto.core.Task
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Buffer
import okio.Sink
import okio.Source

class OkhttpExecutor(
    private val client: OkHttpClient
) : Executor {
    override fun execute(task: Task) {
        val sink: Sink = task.sink ?: return

        val request = Request.Builder()
            .url(task.data ?: error("task url cannot be null"))
            .build()
        val call = client.newCall(request)
        val response = call.execute()

        val source: Source = response.body?.source() ?: return

        val buffer = Buffer()

        val contentLength = response.body?.contentLength()!!
        var total = 0L

        while (source.read(buffer, task.buffer) != -1L) {
            val size = buffer.size
            sink.write(buffer, size)
            total += size
            task.receiver?.invoke(
                Task.Snapshot(total, size, contentLength)
            )
            buffer.clear()
        }

        source.close()
        sink.close()
    }
}