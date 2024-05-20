package com.bittelasia.vlc.annotation

import com.bittelasia.vlc.model.ScaleType

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PlayerSettings(
    val scaleType: ScaleType,
    val preventDeadLock: Boolean,
    val enableDelay: Boolean,
    val showStatus: Boolean
)