package com.bittelasia.vlc.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bittelasia.vlc.annotation.UpdateContents
import com.bittelasia.vlc.fragment.PlayerFragment
import com.bittelasia.vlc.fragment.PlayerVLCFragment
import com.bittelasia.vlc.listener.OnChangeListener
import com.bittelasia.vlc.listener.OnFragmentListener
import com.bittelasia.vlc.listener.OnPermissionListener
import com.bittelasia.vlc.model.VideoInfo
import com.bittelasia.vlc.util.ActivityControl.getInput
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask

abstract class AbstractPlayerActivity<V : ViewDataBinding> : AppCompatActivity(), OnChangeListener, OnFragmentListener,
    OnPermissionListener {

    private var _binding: V? = null
    val binding: V get() = _binding!!

    var videoList: ArrayList<VideoInfo> = arrayListOf()
    var channelIndex = 0

    private var isRestarted = false
    private var playerFragment: PlayerFragment? = null
    private var monitor = 0
    private var timerMonitor: Timer? = null
    private var timerChannel: Timer? = null
    private var timerTaskMonitor: TimerTask? = null
    private var timerTaskChannel: TimerTask? = null
    private var counter: Long = 0
    private var seconds: Long = 0
    private var channelNo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, getLayout())
        setFragment()
        setContent()
    }
    override fun onRestart() {
        super.onRestart()
        isRestarted = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun getLayout(): Int
    open fun setContent() {
        updateObject(this)
    }
    open fun setFragment() {}


    @Throws(Exception::class)
    private fun updateObject(`object`: Any) {
        val clazz: Class<*> = `object`.javaClass
        for (method in clazz.declaredMethods) {
            if (method.isAnnotationPresent(UpdateContents::class.java)) {
                method.isAccessible = true
                try {
                    method.invoke(`object`)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun onPause() {
        super.onPause()
        timerTaskMonitor?.cancel()
        timerTaskChannel?.cancel()
        timerMonitor = null
        timerChannel = null
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
    override fun onFragmentDetached(fragment: Fragment) {
        if (fragment is PlayerFragment) playerFragment = fragment
    }
    fun loadFragments(channels: ArrayList<VideoInfo>) {
        try {
            videoList.addAll(channels)
            channelIndex = 0
            onChannelIndex(0)
            playerMonitor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun playerMonitor() {
        if (timerMonitor == null)
            timerMonitor = Timer()
        timerMonitor?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                try {
                    if (playerFragment != null) {
                        if (seconds < counter - 2) {
                            if (playerFragment is PlayerVLCFragment)
                                if (!(playerFragment as PlayerVLCFragment).getmMediaPlayer()?.isPlaying!!) {
                                    monitor++
                                    onStatus(
                                        Calendar.getInstance().time.toString() + " => " + (playerFragment as PlayerVLCFragment).uriPath + "",
                                        false
                                    )
                                } else {
                                    monitor = 0
                                }
                        } else {
                            monitor = 0
                        }
                    }
                    counter++
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.also { timerTaskMonitor = it }, 0, 1000)
    }

    fun setCounter(counter: Long) {
        this.counter = counter
    }

    fun setSeconds(seconds: Long) {
        this.seconds = seconds
    }

    open fun attachFragment(fragment: Fragment, containerID: Int) {
        runOnUiThread {
            try {
                if (containerID == 0) throw RuntimeException(fragment.tag?.javaClass?.simpleName + " -> Must use non-zero containerViewId")
                supportFragmentManager.beginTransaction().replace(
                    containerID,
                    fragment,
                    fragment.javaClass.simpleName
                ).commitAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun channelTune(pageUp: Boolean): VideoInfo? {
        var video: VideoInfo? = null
        try {
            if (pageUp) {
                channelIndex++
                if (channelIndex < videoList.size) {
                    video = videoList[channelIndex]
                } else {
                    video = videoList[0]
                    channelIndex = 0
                }
            } else {
                channelIndex--
                if (channelIndex >= 0) {
                    video = videoList[channelIndex]
                } else {
                    video = videoList[videoList.size - 1]
                    channelIndex = videoList.size
                }
            }
            onChannelIndex(channelIndex)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return video
    }
    fun channelNumber(event: KeyEvent?): String {
        channelNo += getInput(event!!)
        timerChannel?.purge()
        timerChannel?.cancel()
        timerChannel = null
        timerChannel = Timer()
        timerChannel?.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    var info: VideoInfo?
                    for (x in videoList.indices) {
                        val no = videoList[x].channelNo.toString() + ""
                        info = videoList[x]
                        if (no == channelNo) {
                            channelIndex = x
                            timerChannel?.purge()
                            timerChannel?.cancel()
                            onChannelChanged(info)
                            break
                        }
                    }
                    channelNo = ""
                }
            }
        }.also { timerTaskChannel = it }, 3000)
        return channelNo
    }

    override fun onPermissionGranted() {}

    override fun onPermissionDenied() {}

    override fun onPermissionAlreadyGranted() {}

}