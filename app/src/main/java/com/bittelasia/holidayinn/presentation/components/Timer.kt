
@file:JvmName("TimersKt")
package com.bittelasia.holidayinn.presentation.components

import java.util.Timer
import java.util.TimerTask

public inline fun Timer.schedule(delay: Long, crossinline action: TimerTask.() -> Unit): TimerTask {
    val task = timerTask(action)
    schedule(task, delay)
    return task
}
public inline fun timerTask(crossinline action: TimerTask.() -> Unit): TimerTask = object : TimerTask() {
    override fun run() = action()
}