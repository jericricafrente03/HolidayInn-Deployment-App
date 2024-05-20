package com.bittelasia.holidayinn.presentation.tv.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import com.bittelasia.vlc.R
import com.bittelasia.vlc.core.BaseFragment
import com.bittelasia.vlc.databinding.TvMessageNotificationLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TVNotificationFragment: BaseFragment<TvMessageNotificationLayoutBinding>() {
    private val vm: ApplicationViewModel by viewModels()
    override fun addContents() {
        super.addContents()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.messageNo.collectLatest {
                        binding.apply {
                            if (it > 0){
                                clParent.visibility = View.VISIBLE
                                tvMessage.text = it.toString()
                            }else{
                                clParent.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        vm.getMessage()
    }
    override fun getLayout() = R.layout.tv_message_notification_layout
}