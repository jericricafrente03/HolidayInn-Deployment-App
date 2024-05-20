package com.bittelasia.holidayinn.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bittelasia.holidayinn.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun capitalizeWord(data: String?): String {
    val words = data?.split(" ")
    var newStr = ""
    words?.forEach { word ->
        newStr += word.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        } + " "
    }
    return newStr.trimEnd()
}


fun customDate(oldDate: String?, oldFormat: String, newFormat: String): String? {
    val parser = SimpleDateFormat(oldFormat, Locale.getDefault())
    val formatter = SimpleDateFormat(newFormat, Locale.getDefault())
    val date = oldDate?.let { parser.parse(it) }
    return date?.let { formatter.format(it) }
}

@Composable
fun getDrawableResource(drawableResId: String?): Int {
    return when(drawableResId){
        "01d" -> R.drawable.w01d
        "01n" -> R.drawable.w01n
        "02d" -> R.drawable.w02d
        "02n" -> R.drawable.w02n
        "03d" -> R.drawable.w03d
        "04d" -> R.drawable.w04d
        "04n" -> R.drawable.w04n
        "09d" -> R.drawable.w09d
        "09n" -> R.drawable.w09n
        "10d" -> R.drawable.w10d
        "10n" -> R.drawable.w10n
        "11d" -> R.drawable.w11d
        "11n" -> R.drawable.w11n
        "13d" -> R.drawable.w13d
        "13n" -> R.drawable.w13n
        "50d" -> R.drawable.w50d
        "50n" -> R.drawable.w50n
        else -> R.drawable.weather_not_available
    }
}
@Composable
fun getMessageStatus(status: String?): Int{
    return when(status){
        "0" -> { R.drawable.letter }
        "1" -> { R.drawable.letter }
        else -> { R.drawable.email }
    }
}

@Composable
fun processSpecialCharacters(input: String?): String? {
    return input?.replace("\r\n", " ")
}
@Composable
fun capitalizeAllLetters(input: String): String {
    return input.uppercase()
}
fun removeHttpsFromIpAddress(ipAddress: String): String {
    val regex = Regex("^http://")
    return ipAddress.replace(regex, "")
}
@Composable
fun parseColor(color: String?): Color {
    return Color(android.graphics.Color.parseColor(color ?: defaultColor))
}

fun isJSONValid(test: String?): Boolean {
    try {
        JSONObject(test!!)
    } catch (ex: JSONException) {
        try {
            JSONArray(test)
        } catch (ex1: JSONException) {
            return false
        }
    }
    return true
}

@Composable
fun textToColor(color: String?) : Color{
    return Color(android.graphics.Color.parseColor(color ?: defaultColor))
}