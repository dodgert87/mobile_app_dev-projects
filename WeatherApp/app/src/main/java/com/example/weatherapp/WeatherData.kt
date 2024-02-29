package com.example.weatherapp

data class WeatherData (val day: String, val  description: String, val temperature: Int,val windDirection: Int, val windSpeed: Int){


        fun getDay(day: String):Int{
            return when (day){
                "sunday" -> R.string.sunday
                "monday" -> R.string.Monday
                "tuesday" -> R.string.Tuesday
                "wednesday" -> R.string.Wednesday
                "thursday" -> R.string.Thursday
                "friday" -> R.string.Friday
                "saturday" -> R.string.Saturday
                else -> 0
            }
        }
        fun getDescription(description: String):Int{
            return when(description){
                "sunny" -> R.string.sunny
                "rainy" -> R.string.Rainy
                "clouds" -> R.string.Cloudy
                "mist" -> R.string.mist
                else -> R.string.unknown
            }
        }
        fun getWindDirection(windDirection: Int):Int{
            return  when{
                windDirection >= 337.5 ||  windDirection < 22.5 -> R.string.north
                windDirection >= 22.5 &&  windDirection < 67.5 -> R.string.Northeast
                windDirection >= 67.5 &&  windDirection < 112.5 -> R.string.East
                windDirection >= 157.5 &&  windDirection < 202.5 -> R.string.South
                windDirection >= 202.5 &&  windDirection < 247.5 -> R.string.Southwest
                windDirection >= 247.5 &&  windDirection < 292.5 -> R.string.West
                windDirection >= 292.5 &&  windDirection < 337.5 -> R.string.Northwest
                else -> 0
            }
        }
        fun getIcon(description: String):Int{
            return when(description){
                "sunny" -> R.drawable.sunny_icon
                "rainy" -> R.drawable.rainy_icon
                "clouds" -> R.drawable.cloudy_icon
                "mist" -> R.drawable.mist_icon
                else -> R.drawable.not_found
            }
        }
}

