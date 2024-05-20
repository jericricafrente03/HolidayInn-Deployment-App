package com.bittelasia.holidayinn.presentation.tv.fragment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bittelasia.holidayinn.data.repository.stbpref.manager.LicenseDataStore.readLicense
import com.bittelasia.vlc.R
import com.bittelasia.vlc.core.BaseFragment
import com.bittelasia.vlc.databinding.ExpirationMonitorBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class Expiration : BaseFragment<ExpirationMonitorBinding>() {
    override fun getLayout() = R.layout.expiration_monitor

    override fun addContents() {
        super.addContents()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                requireContext().readLicense(Dispatchers.Main) {
                    monitorLicense(it.END_DATE)
                }
            }
        }
    }


    private fun monitorLicense(inputDate: String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss")
        val licenseDate = LocalDate.parse(inputDate, formatter)
        val currentDate = LocalDate.now()
        val daysUntilExpiration = ChronoUnit.DAYS.between(currentDate, licenseDate)
        binding.apply {
            when {
                daysUntilExpiration < 1 -> {
                    expired = true
                    message = "Subscription expired"
                }
                daysUntilExpiration <= 30 -> {
                    expired = false
                }
                else -> {
                    expired = false
                }
            }
        }
    }
}