package com.bittelasia.holidayinn.data.manager

sealed class Transmitter {
    data object Applist : Transmitter()
    data object Config : Transmitter()
    data object Themes : Transmitter()
    data object Television : Transmitter()
    data object Message : Transmitter()
    data object Facility : Transmitter()
    data object CheckStb : Transmitter()
    data object Reset : Transmitter()
    data object Settings : Transmitter()
    data object ResetLicense : Transmitter()
    data object Broadcast : Transmitter()

    companion object {
        fun xmppToAPI(xmppData: String): Transmitter {
            return when (xmppData) {
                IPTV_UI -> Applist
                CONFIGURATION -> Config
                THEME -> Themes
                TV -> Television
                MESSAGE -> Message
                FACILITY -> Facility
                ALIVE -> CheckStb
                RESET -> Reset
                SETTINGS -> Settings
                RESET_LICENSE -> ResetLicense
                BROADCAST -> Broadcast
                else -> throw IllegalArgumentException("Unknown command: $xmppData")
            }
        }

        private const val FACILITY = "c12566a31ed62ec69b40f65ed1054ce3_get_all_facilities"
        private const val TV = "c12566a31ed62ec69b40f65ed1054ce3_get_all_tv_channel"
        private const val MESSAGE = "c12566a31ed62ec69b40f65ed1054ce3_get_message"
        private const val THEME = "c12566a31ed62ec69b40f65ed1054ce3_get_theme"
        private const val CONFIGURATION = "c12566a31ed62ec69b40f65ed1054ce3_get_config"
        private const val IPTV_UI = "c12566a31ed62ec69b40f65ed1054ce3_get_iptv_ui"
        private const val RESET = "c12566a31ed62ec69b40f65ed1054ce3_reset"
        private const val SETTINGS = "c12566a31ed62ec69b40f65ed1054ce3_settings"
        private const val ALIVE = "check_active_stb"
        private const val RESET_LICENSE = "c12566a31ed62ec69b40f65ed1054ce3_reset_license"
        private const val BROADCAST = "c12566a31ed62ec69b40f65ed1054ce3_get_broadcast_message"
    }
}