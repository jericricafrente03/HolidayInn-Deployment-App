package com.bittelasia.holidayinn.presentation.tv.control

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bittelasia.vlc.databinding.ChannelListViewItemRowBinding
import com.bittelasia.vlc.model.VideoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelAdapter(private val callback: (Int) -> Unit) : ListAdapter<VideoInfo, ChannelAdapter.ViewHolder>(
    ChannelComparator()
) {
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChannelListViewItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        if (current != null) {
            holder.bind(current)
        }
    }

    inner class ViewHolder(
        private val binding: ChannelListViewItemRowBinding,
        private val callback: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideoInfo) {
            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    binding.apply {
                        ivIcon.load(data.icon)
                        tvChannel.text = data.channelNo.toString()
                        tvDescription.text = data.name
                        root.setOnClickListener {
                            callback.invoke(bindingAdapterPosition)
                            it.requestFocus()
                        }
                        root.setOnHoverListener { view, motionEvent ->
                            when (motionEvent!!.actionMasked) {
                                MotionEvent.ACTION_HOVER_ENTER -> {
                                    view.isSelected = true
                                    view.requestFocus()
                                }

                                MotionEvent.ACTION_HOVER_EXIT -> {
                                    view.isSelected = false
                                }
                            }
                            false
                        }
                        root.setOnTouchListener { _, motionEvent ->
                            when (motionEvent!!.action and MotionEvent.ACTION_MASK) {
                                MotionEvent.ACTION_UP -> {
                                    root.performClick()
                                }
                            }
                            false
                        }
                        root.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                            if (b) {
                                view.setBackgroundColor(Color.parseColor("#71eb3d"))
                            } else {
                                view.setBackgroundColor(Color.TRANSPARENT)
                            }
                        }
                        root.setOnKeyListener { _, _, _ ->
                            false
                        }
                        if (bindingAdapterPosition == selectedPosition)
                            root.requestFocus()
                    }
                }
            }
        }
    }

    class ChannelComparator : DiffUtil.ItemCallback<VideoInfo>() {
        override fun areItemsTheSame(
            oldItem: VideoInfo,
            newItem: VideoInfo
        ) = oldItem.channelNo == newItem.channelNo

        override fun areContentsTheSame(
            oldItem: VideoInfo,
            newItem: VideoInfo
        ) = oldItem.channelNo == newItem.channelNo
    }
}