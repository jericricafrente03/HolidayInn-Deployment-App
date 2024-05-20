package com.bittelasia.holidayinn.data.remote

import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.domain.model.app_item.item.AppItem
import com.bittelasia.holidayinn.domain.model.broadcast.Broadcast
import com.bittelasia.holidayinn.domain.model.channel.item.Channel
import com.bittelasia.holidayinn.domain.model.concierge.item.Concierge
import com.bittelasia.holidayinn.domain.model.config.item.SystemConfig
import com.bittelasia.holidayinn.domain.model.facilities.category.FacilitiesCategories
import com.bittelasia.holidayinn.domain.model.facilities.item.Facilities
import com.bittelasia.holidayinn.domain.model.guest.item.Guest
import com.bittelasia.holidayinn.domain.model.license.item.LicenseData
import com.bittelasia.holidayinn.domain.model.license.response.LicenseDate
import com.bittelasia.holidayinn.domain.model.message.item.GetMessage
import com.bittelasia.holidayinn.domain.model.message.readmessage.ReadMessage
import com.bittelasia.holidayinn.domain.model.post_stat.InputStatistics
import com.bittelasia.holidayinn.domain.model.post_stat.StatResponse
import com.bittelasia.holidayinn.domain.model.result.Register
import com.bittelasia.holidayinn.domain.model.theme.item.ZoneTheme
import com.bittelasia.holidayinn.domain.model.time.Time
import com.bittelasia.holidayinn.domain.model.weather.item.GetWeeklyWeatherForecast
import eu.chainfire.libsuperuser.BuildConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeshApi {

    @GET("index.php/apicall/stb_register/{api}/{macAddress}/{room}/{type}/{version}")
    suspend fun registerResult(
        @Path("api") apiKey: String,
        @Path("macAddress") mcAddress: String,
        @Path("room") room: String,
        @Path("type") type: String = "0",
        @Path("version") version: String = BuildConfig.VERSION_NAME
    ): Register

    @POST("index.php/apicall/check_license")
    suspend fun getLicense(
        @Body requestLicense: LicenseData
    ): Response<LicenseDate>

    @POST("index.php/apicall/post_stat")
    suspend fun postStat(
        @Body inputStat: InputStatistics
    ): Response<StatResponse>

    @GET("index.php/apicall/get_theme/{api}")
    suspend fun getThemes(
        @Path("api") apiKey: String = STB.API_KEY
    ): ZoneTheme

    @GET("index.php/apicall/get_config/{api}")
    suspend fun getConfig(
        @Path("api") apiKey: String = STB.API_KEY
    ): SystemConfig

    @GET("index.php/apicall/get_iptv_ui/{api}")
    suspend fun getIptvUI(
        @Path("api") apiKey: String = STB.API_KEY
    ): AppItem

    @GET("index.php/apicall/get_weekly_weather_forecast/{api}")
    suspend fun getWeather(
        @Path("api") apiKey: String = STB.API_KEY
    ): GetWeeklyWeatherForecast

    @GET("index.php/apicall/get_message/{api}/{room}")
    suspend fun getMessage(
        @Path("room") room: String = STB.ROOM,
        @Path("api") apiKey: String = STB.API_KEY
    ): GetMessage

    @GET("index.php/apicall/read_message/{api}/{room}/{id}")
    suspend fun getReadMessage(
        @Path("api") apiKey: String = STB.API_KEY,
        @Path("room") room: String = STB.ROOM,
        @Path("id") id: String
    ): ReadMessage

    @GET("index.php/apicall/get_all_facilities/{api}")
    suspend fun getFacilities(
        @Path("api") apiKey: String = STB.API_KEY
    ): Facilities

    @GET("index.php/apicall/get_facility_category/{api}")
    suspend fun getFacilitiesCategory(
        @Path("api") apiKey: String = STB.API_KEY
    ): FacilitiesCategories

    @GET("index.php/apicall/get_all_virtual_concierge/{api}")
    suspend fun getConcierge(
        @Path("api") apiKey: String = STB.API_KEY
    ): Concierge

    @GET("index.php/apicall/get_all_tv_channel/{api}")
    suspend fun getChannel(
        @Path("api") apiKey: String = STB.API_KEY,
    ): Channel

    @GET("index.php/apicall/get_customer/{api}")
    suspend fun getCustomer(
        @Path("api") apiKey: String = STB.API_KEY,
    ): Guest

    @GET("index.php/apicall/get_broadcast_message/{api}")
    suspend fun getBroadcast(
        @Path("api") apiKey: String = STB.API_KEY,
    ): Broadcast

    @GET("index.php/apicall/stb_time")
    suspend fun getTime(): Time

    @GET("index.php/apicall/check_active_stb2/{api}/{mc}")
    suspend fun showOnline(
        @Path("api") apiKey: String = STB.API_KEY,
        @Path("mc") mcAddress: String = STB.MAC_ADDRESS
        ): String
}