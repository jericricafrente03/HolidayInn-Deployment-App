package com.bittelasia.vlc.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.bittelasia.vlc.R
import com.bittelasia.vlc.model.VideoInfo
import java.util.*

abstract class PlayerStatusFragment : PlayerFragment() {
    private var timer: Timer? = null
    private var parentLayout: ConstraintLayout? = null
    private var tvNo: TextView? = null
    private var tvTitle: TextView? = null
    private var tvStatus: TextView? = null
    private var ivIcon: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.status_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnKeyListener(null)
        tvNo = view.findViewById(R.id.tv_no)
        tvStatus = view.findViewById(R.id.tv_status)
        tvTitle = view.findViewById(R.id.tv_name)
        ivIcon = view.findViewById(R.id.iv_icon)
        parentLayout = view.findViewById(R.id.cl_layout)
    }

    override fun onDetach() {
        super.onDetach()
//        onFragmentListener!!.onFragmentDetached(this)
//        onFragmentListener = null
    }

    open fun updateStatus(activity: AppCompatActivity, source: Any?): PlayerStatusFragment {
        try {
            if (!displayChannel()) {
                if (timer != null) {
                    timer!!.purge()
                    timer!!.cancel()
                }
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        activity.runOnUiThread {
                            try {
                                tvNo?.text = ""
                                tvTitle?.text = ""
                                tvStatus?.text = ""
                                ivIcon?.setImageResource(0)
                                parentLayout?.visibility = View.GONE
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        timer?.purge()
                        timer?.cancel()
                    }
                }, 5000)
            }
            activity.runOnUiThread {
                try {
                    if (source != null) {
                        if (source is VideoInfo) {
                            tvNo?.text = source.channelNo.toString()
                            tvTitle?.text = source.name.toString()
                            tvStatus?.text = source.description.toString()
                            if (ivIcon != null) {
                                val uri = Uri.parse(source.icon)
                                ivIcon?.load(uri)
                            }
                        } else if (source is String) {
                            tvNo?.text = source
                        }
                        parentLayout?.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    abstract fun displayChannel(): Boolean
}