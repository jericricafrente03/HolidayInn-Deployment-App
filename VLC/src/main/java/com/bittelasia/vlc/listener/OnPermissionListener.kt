package com.bittelasia.vlc.listener

interface OnPermissionListener {
    fun onPermissionGranted()
    fun onPermissionDenied()
    fun onPermissionAlreadyGranted()
}