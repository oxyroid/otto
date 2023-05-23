package com.oxy.otto.source

import android.content.Context
import android.util.LruCache
import com.oxy.otto.core.Task
import com.oxy.otto.core.TaskSource
import java.io.File

class AndroidTaskSource(context: Context) : TaskSource {
    private var cache: LruCache<String, Task> = LruCache(64)
    private val dir = context.dataDir

    override fun put(url: String, task: Task) {
        cache.put(url, task)
    }

    override fun remove(url: String) {
        cache.remove(url)
    }

    override fun find(url: String): Task? {
        return cache.get(url)
    }

    override fun restore() {
        cache = dir.list()
            ?.map { File(dir, it) }
            .let { files ->
                LruCache<String, Task>(64).apply {
                    files?.forEach {
                        put(it.nameWithoutExtension, it.toTask())
                    }
                }
            }
    }

    override fun backup() {
        cache.snapshot().forEach { (_, task) ->
            task.toFile()
        }
    }

    private fun File.toTask(): Task = Task.Builder()
        .setData(name)
        .setSingleton(readText().toBooleanStrict())
        .build()

    private fun Task.toFile(): File? {
        if (data == null) return null
        val file = File(dir, data!!)
        if (!file.exists()) {
            file.createNewFile()
        }
        file.outputStream().use { output ->
            output.bufferedWriter().use { writer ->
                writer.append(singleton.toString())
            }
        }
        return file
    }
}