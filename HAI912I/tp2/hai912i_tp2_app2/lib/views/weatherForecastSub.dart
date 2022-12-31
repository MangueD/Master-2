import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

import 'getWeatherIcon.dart';

Widget weatherForecastSub(
    String? weather,
    double? tempLow,
    double? tempHigh,
    double? wind,
    int? humidity,
    String? day){

  return Padding(
    padding: EdgeInsets.symmetric(vertical:30),
    child: Container(
      decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(20)),
          color: Colors.deepPurpleAccent,
      ),

      child: Column(
        children: [
          SizedBox(height: 3),
          Text(
            day!
          ),

          SizedBox(height: 10),

          Row(
            children: [
              Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 10),
                  child: Container(
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(100)),
                        color: Colors.white,
                      ),
                      padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                      child: getWeatherIcon(weatherDescription: weather, color: Colors.pinkAccent, size: 40)
                  )
              ),

              Column(
                children: [
                  Row(
                      children: [
                        Text("${tempLow?.toStringAsFixed(1)} °C",),
                        Icon(Icons.arrow_circle_down),
                      ]
                  ),
                  Row(
                      children: [
                        Text("${tempHigh?.toStringAsFixed(1)} °C",),
                        Icon(Icons.arrow_circle_up),
                      ]
                  ),
                  Text("Hum:${humidity.toString()}%"),
                  Text("Win:${wind?.toStringAsFixed(1)}Km/h"),
                ],
              )
            ],
          )
        ],
      ),
    ),
  );
}