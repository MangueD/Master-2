import 'dart:convert';

import 'package:http/http.dart';

import '../models/weatherForecastModel.dart';
import '../models/weatherModel.dart';

class Network{
  Future<WeatherModel>? getWeather({required String city}) async{

    var finalUrl =
        "https://api.openweathermap.org/data/2.5/weather?q="
        + city
        + "&appid=e40105fe043273b2c0f0fb12c826cff9&units=metric";

    final response = await get(Uri.parse(finalUrl));
    //print("URL: ${Uri.encodeFull(finalUrl)}");

    if(response.statusCode == 200){
      //print("weather data: ${response.body}");
      return WeatherModel.fromJson(json.decode(response.body));
    }
    else{
      throw Exception("Error getting weather forecast");
    }
  }

  Future<WeatherForecastModel>? getWeatherForecast({required String city}) async{
    var finalUrl =
        "https://api.openweathermap.org/data/2.5/forecast?q="
            + city
            + "&appid=e40105fe043273b2c0f0fb12c826cff9&units=metric";

    final response = await get(Uri.parse(finalUrl));
    //print("URL: ${Uri.encodeFull(finalUrl)}");

    if(response.statusCode == 200){
      //print("weather data: ${response.body}");
      return WeatherForecastModel.fromJson(json.decode(response.body));
    }
    else{
      throw Exception("Error getting weather forecast");
    }
  }
}