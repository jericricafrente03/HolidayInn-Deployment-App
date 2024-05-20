package com.bittelasia.holidayinn.presentation.tv.fragment

import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.databinding.StatusReceiverBinding
import com.bittelasia.holidayinn.domain.model.broadcast.BroadCastData
import com.bittelasia.holidayinn.presentation.components.schedule
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import com.bittelasia.vlc.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer

@AndroidEntryPoint
class XmppReceiver : BaseFragment<StatusReceiverBinding>() {
    private val viewModel: ApplicationViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null
    private val hostUrl = "${STB.HOST}:${STB.PORT}/"

    override fun addContents() {
        super.addContents()
        observeBroadcastData()
    }

    private fun observeBroadcastData() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.broadcastReceiver.collectLatest { broadcast ->
                handleBroadcast(broadcast)
            }
        }
    }

    private fun handleBroadcast(broadcast: BroadCastData?) {
        broadcast?.let { data ->
            val duration = data.time * 1000L
            when (data.type) {
                "scrolling" -> handleScrollingBroadcast(data.message, duration)
                "pop" -> handlePopBroadcast(data.message, data.url, hostUrl, duration)
                "emergency" -> showEmergencyMessage(data.message, data.url, duration)
            }
        }
    }

    private fun handleScrollingBroadcast(message: String, duration: Long) {
        binding.myBroadcast.apply {
            visibility = View.VISIBLE
            setText(message)
            startMarquee(duration)
        }
    }

    private fun handlePopBroadcast(message: String, url: String?, hostUrl: String, duration: Long) {
        binding.apply {
            layoutPop.visibility = View.VISIBLE
            tvPop.text = message
            ivPop.loadImageOrDefault(url, hostUrl)
            hideAfterDuration(layoutPop, duration)
        }
    }

    private fun showEmergencyMessage(message: String, url: String?, duration: Long) {
        with(binding) {
            alertParent.visibility = View.VISIBLE
            emergency.visibility = View.VISIBLE
            if (url?.isNotEmpty() == true){
                val imageUri = "$hostUrl$url"
                ivEm.visibility = View.VISIBLE
                ivEm.load(imageUri)
            }else{
                lottieAnimationView.visibility = View.VISIBLE
                ivEm.load(0)
            }
            emergency.setText(message)
            emergency.start()
            audioPlayer()
            scheduleHideEmergencyMessage(duration)
        }
    }

    private fun View.startMarquee(duration: Long) {
        binding.myBroadcast.apply {
            start()
            Timer("Scrolling", false).schedule(duration) {
                CoroutineScope(Dispatchers.Main).launch {
                    stop()
                    visibility = View.GONE
                }
            }
        }
    }

    private fun ImageView.loadImageOrDefault(url: String?, hostUrl: String) {
        load(if (url.isNullOrEmpty()) 0 else "$hostUrl$url")
    }

    private fun hideAfterDuration(view: View, duration: Long) {
        Timer("HideView", false).schedule(duration) {
            CoroutineScope(Dispatchers.Main).launch {
                view.visibility = View.GONE
            }
        }
    }
    private fun scheduleHideEmergencyMessage(duration: Long) {
        Timer("Emergency", false).schedule(duration) {
            lifecycleScope.launch {
                binding.emergency.stop()
                mediaPlayer?.stop()
                binding.alertParent.visibility = View.GONE
                binding.emergency.visibility = View.GONE
            }
        }
    }
    private fun audioPlayer() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.alarm).apply {
                    this.isLooping = true
                    this.start()
                }
            }
        }
    }

    override fun getLayout() = R.layout.status_receiver
}