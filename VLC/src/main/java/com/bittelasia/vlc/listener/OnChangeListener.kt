package com.bittelasia.vlc.listener

interface OnChangeListener {
    fun onBufferChanged(buffer: Float)
    fun onChannelIndex(channelIndex: Int)
    fun onChanging(seconds: Long)
    fun onStatus(message: String?, isPlaying: Boolean)
    fun onChannelChanged(`object`: Any?)
    fun onError(msg: String?)
    fun onEnd()
}