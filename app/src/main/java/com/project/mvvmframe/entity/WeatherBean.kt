package com.project.mvvmframe.entity

/**
 * @CreateDate 2020/5/21 17:24
 * @Author jaylm
 */

data class WeatherBean(
    val isForeign: Int,
    val life: Life,
    val pm25: Pm25,
    val realtime: Realtime,
    val weather: List<WeatherX>
)

data class Life(
    val date: String,
    val info: Info
)

data class Info(
    val chuanyi: List<String>,
    val ganmao: List<String>,
    val kongtiao: List<String>,
    val wuran: Any,
    val xiche: List<String>,
    val yundong: List<String>,
    val ziwaixian: List<String>
)

data class Pm25(
    val cityName: String,
    val dateTime: String,
    val key: String,
    val pm25: Pm25X,
    val show_desc: Any
)

data class Pm25X(
    val curPm: String,
    val des: String,
    val level: String,
    val pm10: String,
    val pm25: String,
    val quality: String
)

data class Realtime(
    val city_code: String,
    val city_name: String,
    val dataUptime: String,
    val date: String,
    val moon: String,
    val time: String,
    val weather: Weather,
    val week: String,
    val wind: Wind
)

data class Weather(
    val humidity: String,
    val img: String,
    val info: String,
    val temperature: String
)

data class Wind(
    val direct: String,
    val offset: String,
    val power: String,
    val windspeed: String
)

data class WeatherX(
    val date: String,
    val info: InfoX,
    val nongli: String,
    val week: String
)

data class InfoX(
    val dawn: List<String>,
    val day: List<String>,
    val night: List<String>
)