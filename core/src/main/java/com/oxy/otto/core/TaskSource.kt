package com.oxy.otto.core

interface TaskSource {
    fun put(url: String, task: Task)
    fun remove(url: String)
    fun find(url: String): Task?
    fun restore()
    fun backup()
}