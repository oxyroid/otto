package com.oxy.otto.core

interface Executor {
    fun execute(source: TaskSource?, task: Task)
}