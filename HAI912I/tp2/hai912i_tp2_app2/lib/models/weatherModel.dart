import 'dart:convert';

class WeatherModel{
  String? city;
  double? temperature;
  double? wind;
  int? humidity;
  String? weather;

  WeatherModel({
    this.city,
    this.weather,
    this.temperature,
    this.wind,
    this.humidity
  });

  WeatherModel.fromJson(Map<String, dynamic> json){
    city = json["name"];
    temperature = json["main"]["temp"];
    wind = json["wind"]["speed"];
    humidity = json["main"]["humidity"];
    weather = json["weather"][0]["main"];
  }
}