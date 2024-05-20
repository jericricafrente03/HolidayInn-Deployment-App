package com.bittelasia.holidayinn.data.repository.stbpref.data

object STB {
    var HOST = ""
        get() = field.ifEmpty { "http://127.0.0.1" }
    var PORT = ""
        get() = field.ifEmpty { "8080" }
    var ROOM = ""
    var MAC_ADDRESS = ""
    var API_KEY = ""
    var USERNAME = ""
    var PASSWORD = ""
    var DEV_ID = ""
    var FIRST_RUN = ""
        get() = field.ifEmpty { "0" }
}
