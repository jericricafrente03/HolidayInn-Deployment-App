package com.bittelasia.holidayinn.data.manager

import com.bittelasia.holidayinn.presentation.components.isJSONValid
import kotlinx.coroutines.flow.flow


object Command {

    private const val TV = "c12566a31ed62ec69b40f65ed1054ce3_get_all_tv_channel"
    private const val FACILITY = "c12566a31ed62ec69b40f65ed1054ce3_get_all_facilities"
    private const val FACILITY_CATEGORY = "c12566a31ed62ec69b40f65ed1054ce3_get_facility_category"
    private const val MESSAGE = "c12566a31ed62ec69b40f65ed1054ce3_get_message"
    private const val THEME = "c12566a31ed62ec69b40f65ed1054ce3_get_theme"
    private const val CONFIGURATION = "c12566a31ed62ec69b40f65ed1054ce3_get_config"
    private const val IPTV_UI = "c12566a31ed62ec69b40f65ed1054ce3_get_iptv_ui"
    private const val RESET = "c12566a31ed62ec69b40f65ed1054ce3_reset"
    private const val SETTINGS = "c12566a31ed62ec69b40f65ed1054ce3_settings"
    private const val ALIVE = "check_active_stb"
    private const val GUEST_CHECK = "c12566a31ed62ec69b40f65ed1054ce3_get_customer"
    private const val CONCIERGE = "c12566a31ed62ec69b40f65ed1054ce3_get_all_virtual_concierge"
    private const val RESET_LICENSE = "c12566a31ed62ec69b40f65ed1054ce3_reset_license"



    fun xmppToAPI(message: String): String? {
        when (message) {
            TV -> { return "Television" }
            FACILITY -> { return "facility" }
            FACILITY_CATEGORY -> { return "Facilities" }
            MESSAGE -> { return "Message" }
            THEME -> { return "Themes" }
            CONFIGURATION -> { return "Config" }
            IPTV_UI -> { return "Applist" }
            ALIVE -> { return "check_stb" }
            RESET -> { return "reset" }
            SETTINGS -> { return "settings" }
            GUEST_CHECK -> { return "guest_check" }
            CONCIERGE -> { return "concierge" }
            RESET_LICENSE -> { return "reset_license" }
            else -> {
                if (isJSONValid(message)){
                    return message
                }
            }
        }
        return null
    }
}