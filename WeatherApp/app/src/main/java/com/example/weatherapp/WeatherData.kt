package com.example.weatherapp

data class WeatherData (val day: String, val  description: String, val temperature: Int,val windDirection: String){


        fun getDay(day: String):Int{
            return when (day){
                "Sunday" -> R.string.sunday
                "Monday" -> R.string.Monday
                "Tuesday" -> R.string.Tuesday
                "Wednesday" -> R.string.Wednesday
                "Thursday" -> R.string.Thursday
                "Friday" -> R.string.Friday
                "Saturday" -> R.string.Saturday
                else -> 0
            }
        }
        fun getDescription(description: String):Int{
            return when(description){
                "Sunny" -> R.string.sunny
                "Rainy" -> R.string.Rainy
                "Cloudy" -> R.string.Cloudy
                else -> 0
            }
        }
        fun getWindDirection(windDirection: String):Int{
            return  when(windDirection){
                "North" -> R.string.north
                "West" -> R.string.West
                "East" -> R.string.East
                "South" -> R.string.South
                "Southeast" -> R.string.Southeast
                "Southwest" -> R.string.Southwest
                "Northeast" -> R.string.Northeast
                "Northwest" -> R.string.Northwest
                else -> 0
            }
        }
        fun getIcon(description: String):Int{
            return when(description){
                "Sunny" -> R.drawable.sunny_icon
                "Rainy" -> R.drawable.rainy_icon
                "Cloudy" -> R.drawable.cloudy_icon
                else -> 0
            }
        }
}

