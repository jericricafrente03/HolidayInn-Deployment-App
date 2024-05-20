@file:Suppress("DEPRECATION")

package com.bittelasia.holidayinn.presentation.tv.control

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.bittelasia.holidayinn.R

class KCustomToast {

    companion object {
        private lateinit var layoutInflater: LayoutInflater

        fun infoToast(context: Activity) {
            layoutInflater = LayoutInflater.from(context)
            val layout = layoutInflater.inflate(
                R.layout.custom_toast_layout,
                (context).findViewById(R.id.custom_toast_layout)
            )
            val toast = Toast(context.applicationContext)
            toast.duration = Toast.LENGTH_LONG
            toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 10)
            toast.view = layout
            toast.show()
        }
    }
}